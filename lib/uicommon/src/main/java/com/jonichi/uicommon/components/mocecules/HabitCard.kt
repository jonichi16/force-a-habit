package com.jonichi.uicommon.components.mocecules

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HabitCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ElevatedCard(
        colors =
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
        elevation =
            CardDefaults.elevatedCardElevation(
                defaultElevation = 6.dp,
            ),
        shape = RoundedCornerShape(4.dp),
        modifier =
            modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
    ) {
        content()
    }
}
