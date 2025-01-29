package com.jonichi.habit.ui.habitlist.data

import com.jonichi.habit.domain.model.Habit

fun getHabitList(): List<Habit> {
    return listOf(
        Habit(title = "Habit 1"),
        Habit(title = "Habit 2"),
        Habit(title = "Habit 3"),
    )
}
