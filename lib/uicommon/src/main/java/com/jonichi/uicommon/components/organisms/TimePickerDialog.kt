package com.jonichi.uicommon.components.organisms

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_BUTTON_CANCEL
import com.jonichi.common.constant.TAG_BUTTON_CONFIRM
import com.jonichi.common.constant.TAG_TIME_INPUT

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
