package com.jonichi.uicommon.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jonichi.uicommon.theme.ForceAHabitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTopAppBar(
    title: String,
    onBackAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (onBackAction != null) {
                IconButton(onClick = onBackAction) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "back button"
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
fun HabitTopAppBarPreview() {
    ForceAHabitTheme {
        HabitTopAppBar(title = "Sample", onBackAction = {})
    }
}