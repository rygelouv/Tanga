package app.books.tanga.feature.home

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.ui.theme.TangaWhiteBackground

@Composable
fun AnimatedShimmerLoader(modifier: Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
    Column(modifier = modifier.background(color = TangaWhiteBackground)) {
        repeat(3) {
            Spacer(modifier = Modifier.height(24.dp))
            AnimatedShimmerRow(brush)
        }
    }
}

@Composable
fun AnimatedShimmerRow(brush: Brush) {
    Row {
        repeat(4) {
            ShimmerSummaryItemSmall(modifier = Modifier, brush = brush)
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun ShimmerPreview() {
    Row {
        repeat(4) {
            ShimmerSummaryItemSmall(
                modifier = Modifier,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.LightGray.copy(alpha = 0.6f),
                        Color.LightGray.copy(alpha = 0.2f),
                        Color.LightGray.copy(alpha = 0.6f),
                    )
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}