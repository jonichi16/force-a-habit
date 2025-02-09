package com.jonichi.uicommon.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun HabitText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    typography: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize ?: typography.fontSize,
        fontWeight = fontWeight ?: typography.fontWeight,
        fontStyle = fontStyle ?: typography.fontStyle,
        fontFamily = fontFamily ?: typography.fontFamily,
        modifier = modifier,
    )
}
