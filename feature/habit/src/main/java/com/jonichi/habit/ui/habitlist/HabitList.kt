package com.jonichi.habit.ui.habitlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jonichi.habit.domain.Habit
import com.jonichi.uicommon.components.HabitTopAppBar
import com.jonichi.uicommon.theme.ForceAHabitTheme

@Composable
fun HabitList(
    uiState: HabitListUiState,
    modifier: Modifier = Modifier,
) {
    Column {
        HabitTopAppBar(title = "Home")
        when (uiState) {
            HabitListUiState.Error -> TODO()
            HabitListUiState.Loading -> {
                Text(text = "Loading...")
            }
            is HabitListUiState.Success -> {
                LazyColumn(
                    modifier =
                        modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                ) {
                    items(uiState.habits) { habit ->
                        Text(text = habit.title)
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HabitListPreview() {
    ForceAHabitTheme {
        val state =
            remember {
                HabitListUiState.Success(
                    habits =
                        listOf(
                            Habit(title = "Habit 1"),
                            Habit(title = "Habit 2"),
                        ),
                )
            }
        HabitList(state)
    }
}
