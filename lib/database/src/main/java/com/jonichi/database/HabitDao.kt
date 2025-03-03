package com.jonichi.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jonichi.database.model.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Upsert
    suspend fun upsert(habitEntity: HabitEntity)

    @Query("SELECT * FROM habit")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitById(id: Int): HabitEntity
}
