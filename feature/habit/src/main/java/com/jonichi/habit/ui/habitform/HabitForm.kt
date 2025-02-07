package com.jonichi.habit.ui.habitform

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jonichi.uicommon.components.HabitTopAppBar

@Composable
fun HabitForm(
    onBackAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HabitTopAppBar(title = "Add Habit", onBackAction = onBackAction)
        Text(text = "Add Habit")
    }
}
