package com.jonichi.habit.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jonichi.uicommon.theme.ForceAHabitTheme

@Composable
fun HabitList(modifier: Modifier = Modifier) {
//    Text(
//        text = "Home",
//        modifier = modifier,
//    )
}

@Preview(showBackground = true)
@Composable
fun HabitListPreview() {
    ForceAHabitTheme {
        HabitList()
    }
}
