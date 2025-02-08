package com.jonichi.habit.ui.habitform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichi.common.constant.SHARING_STARTED_TIMEOUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HabitFormViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<HabitFormUiState>(HabitFormUiState.Loading)
    val uiState =
        _uiState.onStart { loadHabit() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(SHARING_STARTED_TIMEOUT),
                HabitFormUiState.Loading,
            )

    fun updateTitle(title: String) {
        updateSuccessState { state ->
            state.copy(title = title)
        }
    }

    fun updateSchedule(schedule: LocalTime) {
        updateSuccessState { state ->
            state.copy(schedule = schedule)
        }
    }

    fun toggleTimeDialog() {
        updateSuccessState { state ->
            state.copy(isTimeDialogOpen = !state.isTimeDialogOpen)
        }
    }

    private fun loadHabit() {
        viewModelScope.launch {
            _uiState.value =
                HabitFormUiState.Success()
        }
    }

    private inline fun updateSuccessState(update: (HabitFormUiState.Success) -> HabitFormUiState.Success) {
        val currentState = _uiState.value
        if (currentState is HabitFormUiState.Success) {
            _uiState.value = update(currentState)
        }
    }
}
