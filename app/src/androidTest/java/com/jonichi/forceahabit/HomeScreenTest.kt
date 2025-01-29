package com.jonichi.forceahabit

import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jonichi.habit.domain.model.Habit
import com.jonichi.habit.ui.habitlist.HabitList
import com.jonichi.habit.ui.habitlist.HabitListUiState
import com.jonichi.uicommon.theme.ForceAHabitTheme
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_shouldDisplayListOfHabit() {
        composeTestRule.setContent {
            val state =
                remember {
                    HabitListUiState.Success(
                        habits =
                            listOf(
                                Habit(
                                    id = 1,
                                    title = "Habit 1",
                                    schedule = LocalTime.of(12, 0),
                                    isStrict = false,
                                ),
                                Habit(
                                    id = 2,
                                    title = "Habit 2",
                                    schedule = LocalTime.of(12, 0),
                                    isStrict = false,
                                ),
                                Habit(
                                    id = 3,
                                    title = "Habit 3",
                                    schedule = LocalTime.of(12, 0),
                                    isStrict = false,
                                ),
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
