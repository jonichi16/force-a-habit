package com.jonichi.forceahabit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jonichi.habit.ui.habitform.HabitForm
import com.jonichi.habit.ui.habitform.HabitFormViewModel
import com.jonichi.habit.ui.habitlist.HabitList
import com.jonichi.habit.ui.habitlist.HabitListViewModel
import com.jonichi.habit.ui.habittopbar.HabitTopBar
import com.jonichi.habit.ui.habittopbar.HabitTopBarViewModel

@Composable
fun ForceAHabitApp(
    navController: NavHostController = rememberNavController(),
    topBarViewModel: HabitTopBarViewModel = hiltViewModel(),
) {
    val topBarState by topBarViewModel.topBarState.collectAsState()

    Scaffold(
        topBar = { HabitTopBar(state = topBarState) },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier =
                Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
        ) {
            composable(
                route = Screen.Home.route,
            ) {
                val habitState: HabitListViewModel = hiltViewModel()
                val uiState by habitState.uiState.collectAsState()
                topBarViewModel.updateTopBar(title = "Home")

                HabitList(
                    state = uiState,
                    onNavigateToHabitForm =
                        { habitId ->
                            navController.navigate("habitForm/$habitId")
                        },
                    modifier = Modifier.padding(16.dp),
                )
            }
            composable(
                route = Screen.HabitForm.route,
                arguments = listOf(navArgument("habitId") { type = NavType.IntType }),
            ) {
                val habitFormViewModel: HabitFormViewModel = hiltViewModel()
                val state by habitFormViewModel.uiState.collectAsState()
                val onBackAction: () -> Unit = { navController.popBackStack() }
                topBarViewModel.updateTopBar(title = "Add Habit", onBackAction = onBackAction)

                HabitForm(
                    state = state,
                    onEvent = habitFormViewModel::onEvent,
                    onBackAction = onBackAction,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home")

    data object HabitForm : Screen(route = "habitForm/{habitId}")
}
