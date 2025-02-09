package com.jonichi.uicommon.components.mocecules

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jonichi.common.util.timeFormatter
import com.jonichi.uicommon.components.atoms.HabitText
import java.time.LocalTime

@Composable
fun TimeText(
    time: LocalTime,
    color: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier,
) {
    HabitText(
        text = timeFormatter(time),
        color = color,
        modifier = modifier,
    )
}
