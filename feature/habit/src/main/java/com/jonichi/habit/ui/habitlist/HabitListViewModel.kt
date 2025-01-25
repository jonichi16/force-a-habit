package com.jonichi.habit.ui.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichi.habit.domain.Habit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitListViewModel : ViewModel() {
    companion object {
        private const val DELAY_MILLISECONDS = 1000L
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
            delay(DELAY_MILLISECONDS) // Simulate network or database delay
            _uiState.value =
                HabitListUiState.Success(
                    habits =
                        listOf(
                            Habit(title = "Habit 1"),
                            Habit(title = "Habit 2"),
                            Habit(title = "Habit 3"),
                        ),
                )
        }
    }
}
