package com.jonichi.habit.ui.habitlist.viewmodel

import com.jonichi.habit.domain.repository.HabitRepository
import com.jonichi.habit.ui.habitlist.HabitListUiState
import com.jonichi.habit.ui.habitlist.HabitListViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
        viewModel = HabitListViewModel(habitRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState should be Loading then Success`() =
        runTest {
            val job =
                launch {
                    viewModel.uiState.collect {}
                }

            val initialState = viewModel.uiState.first()

            assertEquals(HabitListUiState.Loading, initialState)

            advanceUntilIdle()

            val successState = viewModel.uiState.first() as HabitListUiState.Success
            assertEquals(3, successState.habits.size)

            job.cancel()
        }
}
