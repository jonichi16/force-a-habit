package com.jonichi.forceahabit.ui

import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_FORM_INPUT_FIELD
import com.jonichi.habit.ui.habitform.HabitForm
import com.jonichi.habit.ui.habitform.HabitFormUiState
import com.jonichi.uicommon.theme.ForceAHabitTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime

class HabitFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun habitForm_shouldDisplayAllFields() {
        composeTestRule.setContent {
            val state =
                remember {
                    HabitFormUiState.Success()
                }
            ForceAHabitTheme {
                HabitForm(
                    state = state,
                    onUpdateTitle = {},
                    onBackAction = {},
                    onUpdateSchedule = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(TAG_FORM_INPUT_FIELD).assertCountEquals(2)
    }

    @Test
    fun habitForm_shouldFillTheFields() {
        var title = ""
        var schedule = LocalTime.of(DEFAULT_HOUR, DEFAULT_MINUTE)
        composeTestRule.setContent {
            ForceAHabitTheme {
                HabitForm(
                    state = HabitFormUiState.Success(),
                    onUpdateTitle = { titleInput ->
                        title = titleInput
                    },
                    onUpdateSchedule = { scheduleInput ->
                        schedule = scheduleInput
                    },
                    onBackAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Title").performTextInput("Habit 1")
        assertEquals("Habit 1", title)
    }
}
