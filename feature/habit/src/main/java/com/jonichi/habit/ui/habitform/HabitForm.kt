package com.jonichi.habit.ui.habitform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jonichi.common.constant.DEFAULT_HOUR
import com.jonichi.common.constant.DEFAULT_MINUTE
import com.jonichi.common.constant.TAG_FORM_INPUT_FIELD
import com.jonichi.uicommon.components.HabitTopAppBar
import com.jonichi.uicommon.theme.ForceAHabitTheme
import java.time.LocalTime

@Composable
fun HabitForm(
    onBackAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HabitTopAppBar(title = "Add Habit", onBackAction = onBackAction)
        HabitTextField(
            label = "Title",
            value = "",
            onValueChange = {},
        )
        HabitTextField(
            label = "Time",
            value = LocalTime.of(DEFAULT_HOUR, DEFAULT_MINUTE).toString(),
            readOnly = true,
            onValueChange = {},
            modifier =
                Modifier
                    .clickable {
                        println("")
                    },
        )
    }
}

@Composable
fun HabitTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
) {
    TextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        modifier = modifier.testTag(TAG_FORM_INPUT_FIELD),
    )
}

@PreviewLightDark
@Composable
fun HabitFormPreview() {
    ForceAHabitTheme {
        HabitForm(onBackAction = {})
    }
}
