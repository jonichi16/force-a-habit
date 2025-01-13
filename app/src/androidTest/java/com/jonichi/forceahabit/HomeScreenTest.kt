package com.jonichi.forceahabit

import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jonichi.habit.ui.Habit
import com.jonichi.habit.ui.HabitList
import com.jonichi.habit.ui.HabitUiState
import com.jonichi.uicommon.theme.ForceAHabitTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_shouldDisplayListOfHabit() {
        composeTestRule.setContent {
            val state =
                remember {
                    HabitUiState(
                        habits =
                            listOf(
                                Habit(title = "Habit 1"),
                                Habit(title = "Habit 2"),
                            ),
                    )
                }

            ForceAHabitTheme {
                HabitList(state)
            }
        }
        composeTestRule.onNodeWithText("Habit 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Habit 2").assertIsDisplayed()
    }
}
