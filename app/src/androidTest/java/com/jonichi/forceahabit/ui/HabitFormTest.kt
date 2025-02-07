package com.jonichi.forceahabit.ui

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.jonichi.common.constant.TAG_FORM_INPUT_FIELD
import com.jonichi.habit.ui.habitform.HabitForm
import com.jonichi.uicommon.theme.ForceAHabitTheme
import org.junit.Rule
import org.junit.Test

class HabitFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun habitForm_shouldDisplayAllFields() {
        composeTestRule.setContent {
            ForceAHabitTheme {
                HabitForm(onBackAction = {})
            }
        }

        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(TAG_FORM_INPUT_FIELD).assertCountEquals(2)
    }
}