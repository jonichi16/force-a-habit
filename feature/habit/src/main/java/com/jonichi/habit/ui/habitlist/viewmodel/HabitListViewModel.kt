package com.jonichi.habit.ui.habitlist.viewmodel

import androidx.lifecycle.ViewModel
import com.jonichi.habit.ui.habitlist.Habit
import com.jonichi.habit.ui.habitlist.HabitUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HabitUiState>(HabitUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadHabit()
    }

    private fun loadHabit() {
        _uiState.value =
            HabitUiState.Success(
                habits =
                    listOf(
                        Habit(title = "Habit 1"),
                        Habit(title = "Habit 2"),
                        Habit(title = "Habit 3"),
                    ),
            )
    }
}
