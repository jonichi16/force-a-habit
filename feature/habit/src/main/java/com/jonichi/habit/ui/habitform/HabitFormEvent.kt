package com.jonichi.habit.ui.habitform

import java.time.LocalTime

sealed interface HabitFormEvent {
    data class UpdateTitle(val title: String) : HabitFormEvent

    data class UpdateSchedule(val schedule: LocalTime) : HabitFormEvent

    data object ToggleTimeDialog : HabitFormEvent

    data object SaveHabit : HabitFormEvent

    data object ToggleIsStrict : HabitFormEvent
}
