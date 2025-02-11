package com.jonichi.habit.ui.habitform

import androidx.lifecycle.SavedStateHandle
import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.domain.repository.HabitRepository
import com.jonichi.habit.ui.habitlist.data.getHabitList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
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
    private lateinit var savedStateHandle: SavedStateHandle
    private val habitRepository: HabitRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { habitRepository.upsert(any<Habit>()) } returns Unit

        savedStateHandle = SavedStateHandle(mapOf("habitId" to 0))
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
            val habitSlot = slot<Habit>()
            coEvery { habitRepository.upsert(capture(habitSlot)) } returns Unit

            viewModel = HabitFormViewModel(habitRepository, savedStateHandle)
            advanceUntilIdle()

            viewModel.onEvent(HabitFormEvent.UpdateTitle("Habit 1"))
            viewModel.onEvent(HabitFormEvent.SaveHabit {})
            advanceUntilIdle()

            coVerify(exactly = 1) { habitRepository.upsert(any()) }

            val currentState = viewModel.uiState.first()
            assert(currentState is HabitFormUiState.Success)
            assertEquals("Habit 1", habitSlot.captured.title)
            assertEquals(LocalTime.of(8, 0), habitSlot.captured.schedule)
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
            savedStateHandle["habitId"] = 1
            coEvery { habitRepository.getHabitById(1) } returns getHabitList()[0]

            viewModel = HabitFormViewModel(habitRepository, savedStateHandle)
            advanceUntilIdle()

            val currentState = viewModel.uiState.first() as HabitFormUiState.Success
            assertEquals("Habit 1", currentState.title)
        }

    @Test
    fun `viewModel should update the habit`() =
        runTest {
            val habitToUpdate = getHabitList()[0]
            val habitSlot = slot<Habit>()
            savedStateHandle["habitId"] = 1
            coEvery { habitRepository.getHabitById(1) } returns habitToUpdate
            coEvery { habitRepository.upsert(capture(habitSlot)) } returns Unit

            viewModel = HabitFormViewModel(habitRepository, savedStateHandle)
            advanceUntilIdle()
            viewModel.onEvent(HabitFormEvent.UpdateTitle("Updated Habit"))
            viewModel.onEvent(HabitFormEvent.UpdateSchedule(LocalTime.of(10, 15)))

            viewModel.onEvent(HabitFormEvent.SaveHabit {})
            advanceUntilIdle()

            coVerify(exactly = 1) { habitRepository.upsert(any()) }

            val currentState = viewModel.uiState.first()
            assert(currentState is HabitFormUiState.Success)
            assertEquals("Updated Habit", habitSlot.captured.title)
            assertEquals(LocalTime.of(10, 15), habitSlot.captured.schedule)
        }
}
