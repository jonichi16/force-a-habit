package com.jonichi.habit.ui.habitlist.data

import com.jonichi.habit.domain.model.Habit
import java.time.LocalTime

fun getHabitList(): List<Habit> {
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
