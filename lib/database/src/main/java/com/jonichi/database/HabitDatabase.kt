package com.jonichi.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HabitEntity::class],
    version = 1,
)
abstract class HabitDatabase : RoomDatabase() {
}