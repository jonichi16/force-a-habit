package com.jonichi.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jonichi.database.model.HabitEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalTime
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class HabitDaoTest {
    private lateinit var habitDao: HabitDao
    private lateinit var habitDatabase: HabitDatabase

    private val habit1 =
        HabitEntity(
            title = "Habit 1",
            schedule = LocalTime.of(12, 10),
        )
    private val habit2 =
        HabitEntity(
            title = "Habit 2",
            schedule = LocalTime.of(7, 30),
        )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        habitDatabase =
            Room.inMemoryDatabaseBuilder(context, HabitDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        habitDao = habitDatabase.habitDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        habitDatabase.close()
    }

    private suspend fun addOneHabit() {
        habitDao.upsert(habit1)
    }

    private suspend fun addTwoHabits() {
        habitDao.upsert(habit1)
        habitDao.upsert(habit2)
    }

    @Test
    @Throws(IOException::class)
    fun habitDao_addHabitToDb() =
        runBlocking {
            addOneHabit()
            val allHabits = habitDao.getAllHabits()
            assertEquals(allHabits.size, 1)
            assertEquals(allHabits[0].title, habit1.title)
            assertEquals(allHabits[0].schedule, habit1.schedule)
        }

    @Test
    @Throws(IOException::class)
    fun habitDao_retrieveAllHabits() =
        runBlocking {
            addTwoHabits()
            val allHabits = habitDao.getAllHabits()
            assertEquals(allHabits.size, 2)
        }

}
