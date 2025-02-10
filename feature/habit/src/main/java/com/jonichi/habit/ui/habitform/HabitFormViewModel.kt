package com.jonichi.habit.ui.habitform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HabitFormViewModel
    @Inject
    constructor(
        private val habitRepository: HabitRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val habitId: Int? = savedStateHandle["habitId"]
        private val _uiState = MutableStateFlow<HabitFormUiState>(HabitFormUiState.Loading)
        val uiState = _uiState.asStateFlow()

        init {
            loadHabit()
        }

        fun onEvent(event: HabitFormEvent) {
            when (event) {
                is HabitFormEvent.UpdateTitle -> updateTitle(event.title)
                is HabitFormEvent.UpdateSchedule -> updateSchedule(event.schedule)
                is HabitFormEvent.SaveHabit -> saveHabit(event.onSuccess)
                HabitFormEvent.ToggleTimeDialog -> toggleTimeDialog()
                HabitFormEvent.ToggleIsStrict -> toggleIsStrict()
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

        private fun toggleIsStrict() {
            updateSuccessState { state ->
                state.copy(isStrict = !state.isStrict)
            }
        }

        private fun saveHabit(onSuccess: () -> Unit) {
            viewModelScope.launch {
                updateSuccessState { state ->
                    if (validateTitle(title = state.title)) {
                        val habitToSave =
                            Habit(
                                id = habitId ?: 0,
                                title = state.title,
                                schedule = state.schedule,
                                isStrict = state.isStrict,
                                updatedAt = System.currentTimeMillis()
                            )
                        withContext(Dispatchers.IO) {
                            habitRepository.upsert(habitToSave)
                        }
                        onSuccess()
                        state
                    } else {
                        state.copy(errorMessages = listOf("Title is required"))
                    }
                }
            }
        }

        private fun validateTitle(title: String): Boolean {
            return title.isNotEmpty() || title.isNotBlank()
        }

        private fun loadHabit() {
            _uiState.value = HabitFormUiState.Loading
            viewModelScope.launch {
                if (habitId != 0 && habitId != null) {
                    withContext(Dispatchers.IO) {
                        habitRepository.getHabitById(habitId).let { habit ->
                            _uiState.value =
                                HabitFormUiState.Success(
                                    title = habit.title,
                                    schedule = habit.schedule,
                                    isStrict = habit.isStrict,
                                )
                        }
                    }
                } else {
                    _uiState.value =
                        HabitFormUiState.Success()
                }
            }
        }

        private inline fun updateSuccessState(update: (HabitFormUiState.Success) -> HabitFormUiState.Success) {
            val currentState = _uiState.value
            if (currentState is HabitFormUiState.Success) {
                _uiState.value = update(currentState)
            }
        }
    }
