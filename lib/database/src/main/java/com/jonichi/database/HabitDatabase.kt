package com.jonichi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jonichi.database.model.HabitEntity

@Database(
    entities = [HabitEntity::class],
    version = 1,
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}