package com.jonichi.habit.ui.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichi.habit.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel
    @Inject
    constructor(
        private val habitRepository: HabitRepository,
    ) : ViewModel() {
        companion object {
            private const val SHARING_STARTED_TIMEOUT = 5000L
        }

        private val _uiState = MutableStateFlow<HabitListUiState>(HabitListUiState.Loading)
        val uiState =
            _uiState.onStart { loadHabits() }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(SHARING_STARTED_TIMEOUT),
                    HabitListUiState.Loading,
                )

        private fun loadHabits() {
            viewModelScope.launch {
                _uiState.value =
                    HabitListUiState.Success(
                        habits = habitRepository.getAllHabits().first(),
                    )
            }
        }
    }
