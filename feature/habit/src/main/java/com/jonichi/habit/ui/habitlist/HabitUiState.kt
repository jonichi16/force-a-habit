package com.jonichi.habit.ui.habitlist

sealed interface HabitUiState {
    data object Loading : HabitUiState

    data object Error : HabitUiState

    data class Success(val habits: List<Habit> = listOf()) : HabitUiState
}
