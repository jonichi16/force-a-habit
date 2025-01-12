package com.jonichi.forceahabit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jonichi.forceahabit.ui.theme.ForceAHabitTheme
import com.jonichi.habit.ui.HabitList
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_shouldDisplayListOfHabit() {
        composeTestRule.setContent {
            ForceAHabitTheme {
                HabitList()
            }
        }
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    }
}
