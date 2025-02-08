package com.jonichi.habit.ui.habitform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichi.common.constant.SHARING_STARTED_TIMEOUT
import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HabitFormViewModel
    @Inject
    constructor(
        private val habitRepository: HabitRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<HabitFormUiState>(HabitFormUiState.Loading)
        val uiState =
            _uiState.onStart { loadHabit() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(SHARING_STARTED_TIMEOUT),
                    HabitFormUiState.Loading,
                )

        fun onEvent(event: HabitFormEvent) {
            when (event) {
                is HabitFormEvent.UpdateTitle -> updateTitle(event.title)
                is HabitFormEvent.UpdateSchedule -> updateSchedule(event.schedule)
                HabitFormEvent.ToggleTimeDialog -> toggleTimeDialog()
                HabitFormEvent.SaveHabit -> saveHabit()
            }
        }

        private fun updateTitle(title: String) {
            updateSuccessState { state ->
                state.copy(title = title)
            }
        }

        private fun updateSchedule(schedule: LocalTime) {
            updateSuccessState { state ->
                state.copy(schedule = schedule)
            }
        }

        private fun toggleTimeDialog() {
            updateSuccessState { state ->
                state.copy(isTimeDialogOpen = !state.isTimeDialogOpen)
            }
        }

        private fun saveHabit() {
            viewModelScope.launch {
                updateSuccessState { state ->
                    val habitToSave =
                        Habit(
                            title = state.title,
                            schedule = state.schedule,
                        )
                    habitRepository.upsert(habitToSave)
                    HabitFormUiState.Success()
                }
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
