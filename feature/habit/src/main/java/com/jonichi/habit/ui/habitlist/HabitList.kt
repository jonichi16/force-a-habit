package com.jonichi.habit.ui.habitlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jonichi.common.constant.TAG_ADD_HABIT_NAVIGATION
import com.jonichi.habit.domain.model.Habit
import com.jonichi.uicommon.components.HabitTopAppBar
import com.jonichi.uicommon.theme.ForceAHabitTheme
import java.time.LocalTime

@Composable
fun HabitList(
    state: HabitListUiState,
    onNavigateToHabitForm: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        HabitTopAppBar(title = "Home")
        when (state) {
            HabitListUiState.Error -> TODO()
            HabitListUiState.Loading -> {
                Text(text = "Loading...")
            }
            is HabitListUiState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn {
                        items(state.habits) { habit ->
                            Row {
                                Text(text = habit.title, color = MaterialTheme.colorScheme.onBackground)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = habit.schedule.toString(), color = MaterialTheme.colorScheme.onBackground)
                            }
                        }
                    }
                    FloatingActionButton(
                        onClick = {
                            onNavigateToHabitForm(0)
                            println("click")
                        },
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                                .testTag(TAG_ADD_HABIT_NAVIGATION),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "add habit",
                        )
                    }
                }
            }
        }
    }
}

object Constant {
    const val HOUR = 12
    const val MINUTE = 0
}

@PreviewLightDark
@Composable
fun HabitListPreview() {
    ForceAHabitTheme {
        val defaultSchedule = LocalTime.of(Constant.HOUR, Constant.MINUTE)
        val state =
            remember {
                HabitListUiState.Success(
                    habits =
                        listOf(
                            Habit(id = 1, title = "Habit 1", schedule = defaultSchedule, isStrict = false),
                            Habit(id = 2, title = "Habit 2", schedule = defaultSchedule, isStrict = false),
                            Habit(id = 3, title = "Habit 3", schedule = defaultSchedule, isStrict = false),
                        ),
                )
            }
        HabitList(state = state, onNavigateToHabitForm = {})
    }
}
