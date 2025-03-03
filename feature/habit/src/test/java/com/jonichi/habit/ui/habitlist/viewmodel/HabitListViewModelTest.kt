package com.jonichi.habit.ui.habitlist.viewmodel

import com.jonichi.habit.domain.repository.HabitRepository
import com.jonichi.habit.ui.habitlist.HabitListUiState
import com.jonichi.habit.ui.habitlist.HabitListViewModel
import com.jonichi.habit.ui.habitlist.data.getHabitList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HabitListViewModelTest {
    private lateinit var viewModel: HabitListViewModel
    private val habitRepository: HabitRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { habitRepository.getAllHabits() } returns flowOf(getHabitList())

        viewModel = HabitListViewModel(habitRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState should be Loading then Success`() =
        runTest {
            val successState = viewModel.uiState.first() as HabitListUiState.Success
            assertEquals(3, successState.habits.size)
        }
}
