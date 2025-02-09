package com.jonichi.habit.ui.habitform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_BUTTON_CANCEL
import com.jonichi.common.constant.TAG_BUTTON_CONFIRM
import com.jonichi.common.constant.TAG_FORM_INPUT_FIELD
import com.jonichi.common.constant.TAG_TIME_INPUT
import com.jonichi.uicommon.theme.ForceAHabitTheme
import java.time.LocalTime

@Composable
fun HabitForm(
    state: HabitFormUiState,
    onEvent: (HabitFormEvent) -> Unit,
    onBackAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (state) {
            HabitFormUiState.Error -> TODO()
            HabitFormUiState.Loading -> {
                Text(text = "Loading...")
            }
            is HabitFormUiState.Success -> {
                HabitTextField(
                    label = "Title",
                    value = state.title,
                    onValueChange = { title ->
                        onEvent(HabitFormEvent.UpdateTitle(title))
                    },
                )
                HabitTextField(
                    label = "Time",
                    value = state.schedule.toString(),
                    readOnly = true,
                    enabled = false,
                    onValueChange = {},
                    colors =
                        TextFieldDefaults.colors(
                            disabledIndicatorColor = Color.Gray,
                            disabledTextColor = LocalContentColor.current,
                            disabledLabelColor = LocalContentColor.current,
                        ),
                    modifier =
                        Modifier
                            .clickable(role = Role.Button) {
                                onEvent(HabitFormEvent.ToggleTimeDialog)
                            },
                )
                Button(onClick = {
                    onEvent(HabitFormEvent.SaveHabit)
                    onBackAction()
                }) {
                    Text("Save")
                }
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

@Composable
fun HabitTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.colors(),
) {
    TextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        enabled = enabled,
        colors = colors,
        modifier = modifier.testTag(TAG_FORM_INPUT_FIELD),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onToggleTimeDialog: () -> Unit,
    confirmButton: (hour: Int, minute: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val timePickerState =
        rememberTimePickerState(
            initialHour = DEFAULT_HOUR,
            initialMinute = DEFAULT_MINUTE,
        )
    AlertDialog(
        onDismissRequest = onToggleTimeDialog,
        confirmButton = {
            TextButton(
                onClick = {
                    confirmButton(timePickerState.hour, timePickerState.minute)
                },
                modifier = Modifier.testTag(TAG_BUTTON_CONFIRM),
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onToggleTimeDialog, modifier = Modifier.testTag(TAG_BUTTON_CANCEL)) {
                Text(text = "Cancel")
            }
        },
        title = { Text("Select time") },
        text = {
            TimeInput(
                state = timePickerState,
                modifier = modifier.testTag(TAG_TIME_INPUT),
            )
        },
    )
}

@PreviewLightDark
@Composable
fun HabitFormPreview() {
    ForceAHabitTheme {
        HabitForm(
            state = HabitFormUiState.Success(),
            onEvent = {},
            onBackAction = {},
        )
    }
}
