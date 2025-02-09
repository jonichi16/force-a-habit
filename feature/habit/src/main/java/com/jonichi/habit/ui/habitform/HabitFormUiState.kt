package com.jonichi.habit.ui.habitform

import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import java.time.LocalTime

sealed interface HabitFormUiState {
    data object Loading : HabitFormUiState

    data object Error : HabitFormUiState

    data class Success(
        val title: String = "",
        val schedule: LocalTime = LocalTime.of(DEFAULT_HOUR, DEFAULT_MINUTE),
        val isTimeDialogOpen: Boolean = false,
        val isStrict: Boolean = false
    ) : HabitFormUiState
}
