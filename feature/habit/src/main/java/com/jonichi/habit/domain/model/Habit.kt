package com.jonichi.habit.domain.model

import java.time.LocalTime

data class Habit(
    val id: Int = 0,
    val title: String,
    val schedule: LocalTime,
    val isStrict: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)
