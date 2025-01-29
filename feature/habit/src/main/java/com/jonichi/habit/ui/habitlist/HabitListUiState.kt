package com.jonichi.habit.ui.habitlist

import com.jonichi.habit.domain.model.Habit

sealed interface HabitListUiState {
    data object Loading : HabitListUiState

    data object Error : HabitListUiState

    data class Success(val habits: List<Habit> = listOf()) : HabitListUiState
}
