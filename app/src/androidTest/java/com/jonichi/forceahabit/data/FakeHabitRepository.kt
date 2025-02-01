package com.jonichi.forceahabit.data

import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.domain.repository.HabitRepository
import java.time.LocalTime
import javax.inject.Inject

class FakeHabitRepository
    @Inject
    constructor() : HabitRepository {
        override suspend fun upsert(habit: Habit) {
            TODO("Not yet implemented")
        }

        override suspend fun getAllHabits(): List<Habit> {
            return listOf(
                Habit(
                    id = 1,
                    title = "Habit 1",
                    schedule = LocalTime.of(12, 0),
                    isStrict = false,
                ),
                Habit(
                    id = 2,
                    title = "Habit 2",
                    schedule = LocalTime.of(12, 0),
                    isStrict = false,
                ),
                Habit(
                    id = 3,
                    title = "Habit 3",
                    schedule = LocalTime.of(12, 0),
                    isStrict = false,
                ),
            )
        }
    }
