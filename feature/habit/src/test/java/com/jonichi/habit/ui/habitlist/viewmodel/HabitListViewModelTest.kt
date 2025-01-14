package com.jonichi.habit.ui.habitlist.viewmodel

import com.jonichi.habit.ui.habitlist.HabitUiState
import com.jonichi.habit.ui.habitlist.data.getHabitList
import org.junit.Assert.assertEquals
import org.junit.Test

class HabitListViewModelTest {
    private val viewModel = HabitListViewModel()

    @Test
    fun habitListViewModel_Initialization_ShouldLoadListOfHabit() {
        val state = viewModel.uiState.value
        val habits = getHabitList()

        assertEquals(habits, (state as HabitUiState.Success).habits)
    }
}
