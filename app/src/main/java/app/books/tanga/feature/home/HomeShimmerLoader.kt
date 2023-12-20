package app.books.tanga.feature.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.common.ui.ShimmerSummaryItem
import app.books.tanga.common.ui.ShimmerSummaryListRow
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport

@Composable
fun AnimatedShimmerLoader(modifier: Modifier = Modifier) {
    val shimmerColors =
        listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        )

    val transition = rememberInfiniteTransition(label = "Summary list Shimmer transition")
    val translateAnimation =
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "Summary list Shimmer translate animation"
        )

    val brush =
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        repeat(3) {
            Spacer(modifier = Modifier.height(24.dp))
            ShimmerSummaryListRow(brush)
        }
    }
}

@Preview(showBackground = true)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun ShimmerPreview() {
    Row {
        repeat(4) {
            ShimmerSummaryItem(
                modifier = Modifier,
                brush = Brush.linearGradient(
                    colors =
                    listOf(
                        Color.LightGray.copy(alpha = 0.6f),
                        Color.LightGray.copy(alpha = 0.2f),
                        Color.LightGray.copy(alpha = 0.6f)
                    )
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}
