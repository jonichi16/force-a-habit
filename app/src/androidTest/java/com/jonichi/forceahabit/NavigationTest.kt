package com.jonichi.forceahabit

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupForceAHabitNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
            ForceAHabitApp(navController = navController)
        }
    }

    @Test
    fun fahNavHost_verifyHomeDestination() {
        assertEquals(ForceAHabitScreen.Home.name, navController.currentBackStackEntry?.destination?.route)
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
    }
}
