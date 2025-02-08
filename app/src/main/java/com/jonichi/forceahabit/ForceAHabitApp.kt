package com.jonichi.forceahabit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

@Composable
fun ForceAHabitApp(navController: NavHostController = rememberNavController()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(
                route = Screen.Home.route,
            ) {
                val habitState: HabitListViewModel = hiltViewModel()
                val uiState by habitState.uiState.collectAsState()

                HabitList(
                    state = uiState,
                    onNavigateToHabitForm =
                        { habitId ->
                            navController.navigate("habitForm/$habitId")
                        },
                )
            }
            composable(
                route = Screen.HabitForm.route,
                arguments = listOf(navArgument("habitId") { type = NavType.IntType }),
            ) {
                val habitFormViewModel: HabitFormViewModel = hiltViewModel()
                val state by habitFormViewModel.uiState.collectAsState()

                HabitForm(
                    state = state,
                    onUpdateTitle = habitFormViewModel::updateTitle,
                    onUpdateSchedule = habitFormViewModel::updateSchedule,
                    onToggleTimeDialog = habitFormViewModel::toggleTimeDialog,
                    onBackAction = { navController.popBackStack() },
                    onSave = habitFormViewModel::saveHabit,
                )
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home")

    data object HabitForm : Screen(route = "habitForm/{habitId}")
}
