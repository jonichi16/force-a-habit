package com.jonichi.habit.ui.habitform

import androidx.lifecycle.SavedStateHandle
import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.domain.repository.HabitRepository
import com.jonichi.habit.ui.habitlist.data.getHabitList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
    private val habitRepository: HabitRepository = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { habitRepository.upsert(any<Habit>()) } returns Unit

        coEvery { savedStateHandle.get<Int>(key = "habitId") } returns null

        viewModel = HabitFormViewModel(habitRepository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState should be Loading then Success`() =
        runTest {
            val successState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals(successState.title, "")
            assertEquals(successState.schedule, LocalTime.of(8, 0))
        }

    @Test
    fun `viewModel should be able to update title`() =
        runTest {
            viewModel.onEvent(HabitFormEvent.UpdateTitle("Habit 1"))
            val updatedState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals("Habit 1", updatedState.title)
        }

    @Test
    fun `viewModel should be able to update schedule`() =
        runTest {
            viewModel.onEvent(HabitFormEvent.UpdateSchedule(LocalTime.of(3, 30)))
            val updatedState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals(LocalTime.of(3, 30), updatedState.schedule)
        }

    @Test
    fun `viewModel should be able to open time dialog`() =
        runTest {
            viewModel.onEvent(HabitFormEvent.ToggleTimeDialog)
            val updatedState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals(true, updatedState.isTimeDialogOpen)
        }

    @Test
    fun `viewModel should be able to switch isStrict`() =
        runTest {
            viewModel.onEvent(HabitFormEvent.ToggleIsStrict)
            val updateState = viewModel.uiState.first() as HabitFormUiState.Success
            assert(updateState.isStrict)
        }

    @Test
    fun `viewModel should invoke repository upsert when saving`() =
        runTest {
            viewModel.onEvent(HabitFormEvent.SaveHabit {})
            viewModel.onEvent(HabitFormEvent.UpdateTitle("Habit 1"))
            advanceUntilIdle()

            coVerify {
                habitRepository.upsert(
                    Habit(title = "Habit 1", schedule = LocalTime.of(8, 0)),
                )
            }

            val currentState = viewModel.uiState.first()
            assert(currentState is HabitFormUiState.Success)
        }

    @Test
    fun `viewModel should validate title`() =
        runTest {
            viewModel.onEvent(HabitFormEvent.SaveHabit {})
            advanceUntilIdle()

            val currentState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals(1, currentState.errorMessages.size)
            coVerify(exactly = 0) {
                habitRepository.upsert(
                    any<Habit>(),
                )
            }
        }

    @Test
    fun `viewModel should load correct habit`() =
        runTest {
            coEvery { savedStateHandle.get<Int>(key = "habitId") } returns 1
            coEvery { habitRepository.getHabitById(1) } returns getHabitList()[0]

            viewModel = HabitFormViewModel(habitRepository, savedStateHandle)

            advanceUntilIdle()

            val currentState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals("Habit 1", currentState.title)
        }
}
