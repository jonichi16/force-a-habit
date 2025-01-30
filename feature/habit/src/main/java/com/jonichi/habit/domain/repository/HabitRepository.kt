package com.jonichi.habit.domain.repository

import com.jonichi.habit.domain.model.Habit

interface HabitRepository {
    suspend fun upsert(habit: Habit)

    suspend fun getAllHabits(): List<Habit>
}
