package com.jonichi.habit.ui.habittopbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jonichi.common.constant.TAG_BACK_ARROW
import com.jonichi.uicommon.theme.ForceAHabitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTopBar(
    state: TopBarState,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = state.title) },
        navigationIcon = {
            if (state.onBackAction != null) {
                IconButton(
                    onClick = state.onBackAction,
                    modifier = Modifier.testTag(TAG_BACK_ARROW),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "back button",
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
fun HabitTopBarPreview() {
    ForceAHabitTheme {
        val state = TopBarState(title = "Home")
        HabitTopBar(state)
    }
}
