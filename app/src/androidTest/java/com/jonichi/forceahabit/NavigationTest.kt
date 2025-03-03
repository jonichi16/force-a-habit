package com.jonichi.forceahabit

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jonichi.common.constant.TAG_ADD_HABIT_NAVIGATION
import com.jonichi.common.constant.TAG_BACK_ARROW
import com.jonichi.common.constant.TAG_EDIT_ICON
import com.jonichi.habit.di.RepositoryModule
import com.jonichi.habit.domain.repository.HabitRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: HabitRepository

    @Before
    fun setupForceAHabitNavHost() {
        hiltRule.inject()
    }

    @Test
    fun fahNavHost_verifyHomeDestination() {
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Habit 1").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_BACK_ARROW).assertIsNotDisplayed()
        composeTestRule.onAllNodesWithTag(TAG_EDIT_ICON).assertCountEquals(3)
    }

    @Test
    fun fahNavHost_verifyHabitFormDestination() {
        composeTestRule.onNodeWithTag(TAG_ADD_HABIT_NAVIGATION).performClick()
        composeTestRule.onNodeWithTag(TAG_BACK_ARROW).assertIsDisplayed()
    }

    @Test
    fun fahNavHost_verifyHabitFormToNavigateBack() {
        composeTestRule.onNodeWithTag(TAG_ADD_HABIT_NAVIGATION).performClick()
        composeTestRule.onNodeWithTag(TAG_BACK_ARROW).performClick()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    }

    @Test
    fun fahNavHost_verifyHabitFormToNavigateHomeAfterSaving() {
        composeTestRule.onNodeWithTag(TAG_ADD_HABIT_NAVIGATION).performClick()
        composeTestRule.onNodeWithText("Title").performTextInput("Habit 3")
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    }

    @Test
    fun fahNavHost_verifyHomeToNavigateToHabitFormWhenEditing() {
        composeTestRule.onAllNodesWithTag(TAG_EDIT_ICON)[0].performClick()
        composeTestRule.onNodeWithText("Add Habit").assertIsDisplayed()
        composeTestRule.onNodeWithText("Habit 1").assertIsDisplayed()
    }
}
