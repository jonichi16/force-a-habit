package com.jonichi.habit.ui.habitlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_ADD_HABIT_NAVIGATION
import com.jonichi.habit.domain.model.Habit
import com.jonichi.uicommon.components.atoms.HabitText
import com.jonichi.uicommon.components.mocecules.HabitCard
import com.jonichi.uicommon.components.mocecules.StrictBadge
import com.jonichi.uicommon.components.mocecules.TimeText
import com.jonichi.uicommon.theme.ForceAHabitTheme
import java.time.LocalTime

@Composable
fun HabitList(
    state: HabitListUiState,
    onNavigateToHabitForm: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (state) {
            HabitListUiState.Error -> TODO()
            HabitListUiState.Loading -> {
                HabitText(text = "Loading...")
            }
            is HabitListUiState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn {
                        items(state.habits) { habit ->
                            HabitCard {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxSize().padding(8.dp),
                                ) {
                                    HabitText(
                                        text = habit.title,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        typography = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                                    )
                                    Column {
                                        TimeText(
                                            time = habit.schedule,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                        )
                                        if (habit.isStrict) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            StrictBadge(modifier = Modifier.align(Alignment.End))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    FloatingActionButton(
                        onClick = {
                            onNavigateToHabitForm(0)
                        },
                        shape = CircleShape,
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

@PreviewLightDark
@Composable
fun HabitListPreview() {
    ForceAHabitTheme {
        val defaultSchedule = LocalTime.of(DEFAULT_HOUR, DEFAULT_MINUTE)
        val state =
            remember {
                HabitListUiState.Success(
                    habits =
                        listOf(
                            Habit(id = 1, title = "Habit 1", schedule = defaultSchedule, isStrict = false),
                            Habit(id = 2, title = "Habit 2", schedule = defaultSchedule, isStrict = true),
                            Habit(id = 3, title = "Habit 3", schedule = defaultSchedule, isStrict = false),
                        ),
                )
            }
        HabitList(state = state, onNavigateToHabitForm = {})
    }
}
