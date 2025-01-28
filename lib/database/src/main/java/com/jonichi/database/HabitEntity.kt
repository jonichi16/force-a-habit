package com.jonichi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "habit")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val schedule: LocalTime,
    @ColumnInfo(name = "is_strict")
    val isStrict: Boolean,
)
