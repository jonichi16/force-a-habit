package com.jonichi.habit.ui.habitlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_ADD_HABIT_NAVIGATION
import com.jonichi.common.constant.TAG_EDIT_ICON
import com.jonichi.common.util.timeFormatter
import com.jonichi.habit.domain.model.Habit
import com.jonichi.uicommon.components.atoms.HabitIconButton
import com.jonichi.uicommon.components.atoms.HabitText
import com.jonichi.uicommon.components.mocecules.HabitCard
import com.jonichi.uicommon.components.mocecules.StrictBadge
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
                            HabitCard(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                ) {
                                    HabitTitle(habit = habit)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    HabitDetails(habit = habit)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    HorizontalDivider()
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                    ) {
                                        HabitIconButton(
                                            imageVector = Icons.Filled.Edit,
                                            onClick = { onNavigateToHabitForm(habit.id) },
                                            contentDescription = "Edit habit",
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.size(20.dp).testTag(TAG_EDIT_ICON),
                                        )
                                    }
                                }
                            }
                        }
                    }
                    HabitFloatingActionButton(
                        onClick = {
                            onNavigateToHabitForm(0)
                        },
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                                .testTag(TAG_ADD_HABIT_NAVIGATION),
                    )
                }
            }
        }
    }
}

@Composable
fun HabitFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "add habit",
        )
    }
}

@Composable
fun HabitTitle(
    habit: Habit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier.fillMaxWidth(),
    ) {
        HabitText(
            text = habit.title,
            color = MaterialTheme.colorScheme.onPrimary,
            typography = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f).padding(end = 8.dp),
        )
        if (habit.isStrict) {
            StrictBadge()
        }
    }
}

@Composable
fun HabitDetails(
    habit: Habit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        HabitText(
            text = timeFormatter(habit.schedule),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Medium,
        )
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
