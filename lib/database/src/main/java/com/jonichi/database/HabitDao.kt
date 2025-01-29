package com.jonichi.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface HabitDao {
    @Upsert
    suspend fun upsert(habitEntity: HabitEntity)

    @Query("SELECT * FROM habit")
    suspend fun getAllHabits(): List<HabitEntity>
}