package com.jonichi.habit.ui.habitform

import junit.framework.TestCase.assertEquals
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
import org.junit.Before
import org.junit.Test
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class HabitFormViewModelTest {
    private lateinit var viewModel: HabitFormViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HabitFormViewModel()
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

            assertEquals(HabitFormUiState.Loading, initialState)

            advanceUntilIdle()

            val successState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals(successState.title, "")
            assertEquals(successState.schedule, LocalTime.of(8, 0))

            job.cancel()
        }

    @Test
    fun `viewModel should be able to update title`() =
        runTest {
            val job =
                launch {
                    viewModel.uiState.collect {}
                }

            advanceUntilIdle()
            viewModel.updateTitle("Habit 1")
            advanceUntilIdle()

            val updatedState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals("Habit 1", updatedState.title)

            job.cancel()
        }

    @Test
    fun `viewModel should be able to update schedule`() =
        runTest {
            val job =
                launch {
                    viewModel.uiState.collect {}
                }

            advanceUntilIdle()
            viewModel.updateSchedule(LocalTime.of(3, 30))
            advanceUntilIdle()

            val updatedState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals(LocalTime.of(3, 30), updatedState.schedule)

            job.cancel()
        }

    @Test
    fun `viewModel should be able to open time dialog`() =
        runTest {
            val job =
                launch {
                    viewModel.uiState.collect {}
                }

            advanceUntilIdle()
            viewModel.toggleTimeDialog()
            advanceUntilIdle()

            val updatedState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals(true, updatedState.isTimeDialogOpen)

            job.cancel()
        }
}
