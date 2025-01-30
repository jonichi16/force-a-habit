package com.jonichi.forceahabit

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.jonichi.uicommon.theme.ForceAHabitTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupForceAHabitNavHost() {
        composeTestRule.setContent {
            ForceAHabitTheme {
                navController =
                    TestNavHostController(LocalContext.current).apply {
                        navigatorProvider.addNavigator(ComposeNavigator())
                    }
                ForceAHabitApp(navController = navController)
            }
        }
    }

    @Test
    fun fahNavHost_verifyHomeDestination() {
        assertEquals(ForceAHabitScreen.Home.name, navController.currentBackStackEntry?.destination?.route)
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    }
}
