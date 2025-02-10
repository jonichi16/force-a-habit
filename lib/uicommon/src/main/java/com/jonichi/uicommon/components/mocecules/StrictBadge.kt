package com.jonichi.uicommon.components.mocecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jonichi.common.constant.TAG_STRICT_BADGE
import com.jonichi.uicommon.components.atoms.HabitText

@Composable
fun StrictBadge(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .width(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(16.dp),
                )
                .background(MaterialTheme.colorScheme.tertiary),
    ) {
        HabitText(
            text = "Strict",
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Bold,
            typography = MaterialTheme.typography.labelSmall,
            modifier =
                Modifier
                    .padding(2.dp)
                    .align(Alignment.Center)
                    .testTag(TAG_STRICT_BADGE),
        )
    }
}
