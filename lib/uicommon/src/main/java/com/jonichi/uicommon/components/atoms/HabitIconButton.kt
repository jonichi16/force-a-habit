package com.jonichi.uicommon.components.atoms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jonichi.uicommon.theme.ForceAHabitTheme

@Composable
fun HabitIconButton(
    onClick: () -> Unit,
    contentDescription: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onBackground,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }

}

@PreviewLightDark
@Composable
fun HabitIconButtonPreview() {
    ForceAHabitTheme {
        HabitIconButton(
            onClick = {},
            imageVector = Icons.Filled.Edit,
            contentDescription = ""
        )
    }
}
