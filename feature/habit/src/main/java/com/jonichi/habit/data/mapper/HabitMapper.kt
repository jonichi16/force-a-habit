package com.jonichi.habit.data.mapper

import com.jonichi.database.model.HabitEntity
import com.jonichi.habit.domain.model.Habit

fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = id,
        title = title,
        schedule = schedule,
        isStrict = isStrict,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        title = title,
        schedule = schedule,
        isStrict = isStrict,
    )
}
