package com.jonichi.habit.ui.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonichi.habit.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel
    @Inject
    constructor(
        private val habitRepository: HabitRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<HabitListUiState>(HabitListUiState.Loading)
        val uiState = _uiState.asStateFlow()

        init {
            loadHabits()
        }

        private fun loadHabits() {
            _uiState.value = HabitListUiState.Loading
            viewModelScope.launch {
                habitRepository.getAllHabits().collect { habits ->
                    _uiState.value = HabitListUiState.Success(habits)
                }
            }
        }
    }
