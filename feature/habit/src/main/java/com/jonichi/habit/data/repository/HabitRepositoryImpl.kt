package com.jonichi.habit.data.repository

import com.jonichi.database.HabitDao
import com.jonichi.habit.data.mapper.toDomain
import com.jonichi.habit.data.mapper.toEntity
import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepositoryImpl
    @Inject
    constructor(
        private val habitDao: HabitDao,
    ) : HabitRepository {
        override suspend fun upsert(habit: Habit) {
            habitDao.upsert(habit.toEntity())
        }

        override suspend fun getAllHabits(): Flow<List<Habit>> {
            return habitDao.getAllHabits().map { habitEntities ->
                habitEntities.map { habitEntity ->
                    habitEntity.toDomain()
                }
            }
        }

        override suspend fun getHabitById(id: Int): Habit {
            return habitDao.getHabitById(id).toDomain()
        }
}
