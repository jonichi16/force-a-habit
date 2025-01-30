package com.jonichi.habit.data.repository

import com.jonichi.database.HabitDao
import com.jonichi.habit.data.mapper.toDomain
import com.jonichi.habit.data.mapper.toEntity
import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.domain.repository.HabitRepository
import javax.inject.Inject

class HabitRepositoryImpl
    @Inject
    constructor(
        private val habitDao: HabitDao,
    ) : HabitRepository {
        override suspend fun upsert(habit: Habit) {
            habitDao.upsert(habit.toEntity())
        }

        override suspend fun getAllHabits(): List<Habit> {
            return habitDao.getAllHabits().map { habitEntity -> habitEntity.toDomain() }
        }
    }
