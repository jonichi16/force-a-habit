package com.jonichi.uicommon.components.mocecules

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role

@Composable
fun HabitClickableField(
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HabitTextField(
        label = label,
        value = value,
        readOnly = true,
        enabled = false,
        onValueChange = {},
        colors =
            TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Gray,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
            ),
        modifier =
            modifier
                .clickable(role = Role.Button) {
                    onClick()
                },
    )
}
