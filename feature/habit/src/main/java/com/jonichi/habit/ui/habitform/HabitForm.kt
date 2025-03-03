package com.jonichi.habit.ui.habitform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jonichi.common.util.timeFormatter
import com.jonichi.uicommon.components.atoms.HabitButton
import com.jonichi.uicommon.components.mocecules.HabitClickableField
import com.jonichi.uicommon.components.mocecules.HabitSwitch
import com.jonichi.uicommon.components.mocecules.HabitTextField
import com.jonichi.uicommon.components.organisms.TimePickerDialog
import com.jonichi.uicommon.theme.ForceAHabitTheme
import java.time.LocalTime

@Composable
fun HabitForm(
    state: HabitFormUiState,
    onEvent: (HabitFormEvent) -> Unit,
    onScheduleHabit: (Int, Int) -> Unit,
    onBackAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (state) {
            HabitFormUiState.Error -> TODO()
            HabitFormUiState.Loading -> {
                Text(text = "Loading...")
            }
            is HabitFormUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    HabitTextField(
                        label = "Title",
                        value = state.title,
                        onValueChange = { title ->
                            onEvent(HabitFormEvent.UpdateTitle(title))
                        },
                        errorMessages = state.errorMessages,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    HabitClickableField(
                        label = "Time",
                        value = timeFormatter(state.schedule),
                        onClick = { onEvent(HabitFormEvent.ToggleTimeDialog) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    HabitSwitch(
                        label = "Strict?",
                        checked = state.isStrict,
                        onCheckedChange = { onEvent(HabitFormEvent.ToggleIsStrict) },
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                HabitButton(
                    label = "Save",
                    onClick = {
                        onEvent(HabitFormEvent.SaveHabit(onBackAction))
                        onScheduleHabit(state.schedule.hour, state.schedule.minute)
                    },
                )
                if (state.isTimeDialogOpen) {
                    TimePickerDialog(
                        onToggleTimeDialog = { onEvent(HabitFormEvent.ToggleTimeDialog) },
                        confirmButton = { hour, minute ->
                            onEvent(HabitFormEvent.UpdateSchedule(LocalTime.of(hour, minute)))
                            onEvent(HabitFormEvent.ToggleTimeDialog)
                        },
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HabitFormPreview() {
    ForceAHabitTheme {
        HabitForm(
            state = HabitFormUiState.Success(),
            onEvent = {},
            onScheduleHabit = { _, _ -> {} },
            onBackAction = {},
        )
    }
}
