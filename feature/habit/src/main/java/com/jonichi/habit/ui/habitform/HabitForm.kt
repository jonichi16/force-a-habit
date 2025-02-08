package com.jonichi.habit.ui.habitform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_FORM_INPUT_FIELD
import com.jonichi.common.constant.TAG_TIME_DIALOG
import com.jonichi.uicommon.components.HabitTopAppBar
import com.jonichi.uicommon.theme.ForceAHabitTheme
import java.time.LocalTime

@Composable
fun HabitForm(
    state: HabitFormUiState,
    onUpdateTitle: (String) -> Unit,
    onUpdateSchedule: (LocalTime) -> Unit,
    onToggleTimeDialog: () -> Unit,
    onBackAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HabitTopAppBar(title = "Add Habit", onBackAction = onBackAction)
        when (state) {
            HabitFormUiState.Error -> TODO()
            HabitFormUiState.Loading -> {
                Text(text = "Loading...")
            }
            is HabitFormUiState.Success -> {
                println("Hello")
                HabitTextField(
                    label = "Title",
                    value = state.title,
                    onValueChange = onUpdateTitle,
                )
                HabitTextField(
                    label = "Time",
                    value = state.schedule.toString(),
                    readOnly = true,
                    enabled = false,
                    onValueChange = {},
                    colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Gray,
                        disabledTextColor = LocalContentColor.current,
                        disabledLabelColor = LocalContentColor.current,
                    ),
                    modifier =
                        Modifier
                            .clickable(role = Role.Button) {
                                onToggleTimeDialog()
                            }
                )
                if (state.isTimeDialogOpen) {
                    Text(text = "Time Dialog", modifier=Modifier.testTag(TAG_TIME_DIALOG))
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

@PreviewLightDark
@Composable
fun HabitFormPreview() {
    ForceAHabitTheme {
        HabitForm(
            state = HabitFormUiState.Success(),
            onUpdateTitle = {},
            onBackAction = {},
            onUpdateSchedule = {},
            onToggleTimeDialog = {}
        )
    }
}
