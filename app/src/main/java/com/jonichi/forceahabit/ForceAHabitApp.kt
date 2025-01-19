package com.jonichi.forceahabit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jonichi.habit.ui.habitlist.HabitList
import com.jonichi.habit.ui.habitlist.HabitUiState

@Composable
fun ForceAHabitApp(navController: NavHostController = rememberNavController()) {
    val state =
        remember {
            HabitUiState.Success(
                habits = listOf(),
            )
        }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = ForceAHabitScreen.Home.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(
                route = ForceAHabitScreen.Home.name,
            ) {
                HabitList(state)
            }
        }
    }
}

enum class ForceAHabitScreen(
    @StringRes val title: Int,
) {
    Home(title = R.string.app_name),
}
