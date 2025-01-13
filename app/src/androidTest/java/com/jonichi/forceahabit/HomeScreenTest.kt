package com.jonichi.forceahabit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jonichi.habit.ui.HabitList
import com.jonichi.uicommon.theme.ForceAHabitTheme
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
    }
}
