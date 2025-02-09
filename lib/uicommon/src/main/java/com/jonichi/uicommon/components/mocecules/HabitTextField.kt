package com.jonichi.uicommon.components.mocecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jonichi.common.constant.TAG_FORM_INPUT_FIELD
import com.jonichi.uicommon.components.atoms.HabitText
import com.jonichi.uicommon.theme.ForceAHabitTheme

@Composable
fun HabitTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessages: List<String> = emptyList(),
    readOnly: Boolean = false,
    enabled: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.colors(),
) {
    Column(modifier = Modifier.testTag(TAG_FORM_INPUT_FIELD)) {
        TextField(
            label = { HabitText(text = label) },
            value = value,
            onValueChange = onValueChange,
            isError = errorMessages.isNotEmpty(),
            readOnly = readOnly,
            enabled = enabled,
            colors = colors,
            modifier = modifier,
        )

        if (errorMessages.isNotEmpty()) {
            Column(modifier = Modifier.padding(start = 8.dp)) {
                errorMessages.forEach { error ->
                    Row {
                        HabitText(
                            text = "•",
                            color = MaterialTheme.colorScheme.error,
                            typography = MaterialTheme.typography.labelLarge,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        HabitText(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            typography = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HabitTextFieldPreview() {
    ForceAHabitTheme {
        HabitTextField(
            label = "Test",
            value = "test",
            onValueChange = {},
        )
    }
}

@PreviewLightDark
@Composable
fun HabitTextFieldErrorPreview() {
    ForceAHabitTheme {
        HabitTextField(
            label = "Test",
            value = "",
            onValueChange = {},
            errorMessages =
                listOf(
                    "Test is required",
                    "Sample error",
                ),
        )
    }
}
