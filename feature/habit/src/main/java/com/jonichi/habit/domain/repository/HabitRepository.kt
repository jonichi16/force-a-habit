package com.jonichi.habit.domain.repository

import com.jonichi.habit.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun upsert(habit: Habit)

    suspend fun getAllHabits(): Flow<List<Habit>>
}
