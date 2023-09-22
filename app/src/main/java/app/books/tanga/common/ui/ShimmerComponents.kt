package app.books.tanga.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

/**
 * A list of summary shimmer placeholders
 */
@Composable
fun ShimmerSummaryListRow(brush: Brush) {
    Row {
        repeat(4) {
            ShimmerSummaryItemSmall(modifier = Modifier, brush = brush)
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

/**
 * A summary shimmer item placeholder
 */
@Composable
fun ShimmerSummaryItemSmall(modifier: Modifier, brush: Brush) {
    Column(
        modifier = modifier
            .width(134.dp)
            .height(250.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(brush))

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .height(20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(brush))
    }
}