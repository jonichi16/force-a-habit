package com.jonichi.forceahabit.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_BUTTON_CANCEL
import com.jonichi.common.constant.TAG_BUTTON_CONFIRM
import com.jonichi.common.constant.TAG_FORM_INPUT_FIELD
import com.jonichi.common.constant.TAG_SWITCH
import com.jonichi.common.constant.TAG_TIME_INPUT
import com.jonichi.habit.ui.habitform.HabitForm
import com.jonichi.habit.ui.habitform.HabitFormEvent
import com.jonichi.habit.ui.habitform.HabitFormUiState
import com.jonichi.uicommon.theme.ForceAHabitTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime

class HabitFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpHabitForm() {
        val title = mutableStateOf("")
        val schedule = mutableStateOf(LocalTime.of(DEFAULT_HOUR, DEFAULT_MINUTE))
        val isTimeDialogOpen = mutableStateOf(false)
        val isStrict = mutableStateOf(false)
        val errorMessages = mutableStateOf<List<String>>(emptyList())
        composeTestRule.setContent {
            ForceAHabitTheme {
                HabitForm(
                    state =
                        HabitFormUiState.Success(
                            title = title.value,
                            schedule = schedule.value,
                            isTimeDialogOpen = isTimeDialogOpen.value,
                            isStrict = isStrict.value,
                            errorMessages = errorMessages.value,
                        ),
                    onEvent = { event ->
                        when (event) {
                            is HabitFormEvent.UpdateTitle -> {
                                title.value = event.title
                            }
                            is HabitFormEvent.UpdateSchedule -> {
                                schedule.value = event.schedule
                            }
                            is HabitFormEvent.SaveHabit -> {
                                if (title.value.isBlank() || title.value.isEmpty()) {
                                    errorMessages.value = listOf("Title is required")
                                }
                            }
                            HabitFormEvent.ToggleTimeDialog -> {
                                isTimeDialogOpen.value = !isTimeDialogOpen.value
                            }
                            HabitFormEvent.ToggleIsStrict -> {
                                isStrict.value = !isStrict.value
                            }
                        }
                    },
                    onScheduleHabit = { _, _ -> run {} },
                    onBackAction = {},
                )
            }
        }
    }

    @Test
    fun habitForm_shouldDisplayAllFields() {
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(TAG_FORM_INPUT_FIELD).assertCountEquals(2)
        composeTestRule.onNodeWithTag(TAG_SWITCH).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_SWITCH).assertHasClickAction()
        composeTestRule.onNodeWithText("Strict?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertHasClickAction()
    }

    @Test
    fun habitForm_shouldBeAbleToOpenTimeDialog() {
        composeTestRule.onNodeWithTag(TAG_TIME_INPUT).assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Time").performClick()
        composeTestRule.onNodeWithTag(TAG_TIME_INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_BUTTON_CONFIRM).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_BUTTON_CANCEL).assertIsDisplayed()
    }

    @Test
    fun habitForm_shouldFillTheFields() {
        composeTestRule.onNodeWithText("Title").performTextInput("Habit 1")
        composeTestRule.onNodeWithText("Habit 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time").performClick()
        composeTestRule.onNodeWithText("08").performTextReplacement("04")
        composeTestRule.onNodeWithText("00").performTextReplacement("40")
        composeTestRule.onNodeWithTag(TAG_BUTTON_CONFIRM).performClick()
        composeTestRule.onNodeWithText("04:40 AM").assertIsDisplayed()
    }

    @Test
    fun habitForm_shouldDisplayErrorMessages() {
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("Title is required").assertIsDisplayed()
    }
}
