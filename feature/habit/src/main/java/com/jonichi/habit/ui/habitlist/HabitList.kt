package com.jonichi.habit.ui.habitlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jonichi.uicommon.theme.ForceAHabitTheme

@Composable
fun HabitList(
    state: HabitUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(state.habits) { habit ->
            Text(text = habit.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitListPreview() {
    ForceAHabitTheme {
        val state =
            remember {
                HabitUiState(
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
