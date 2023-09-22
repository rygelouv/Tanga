package app.books.tanga.feature.summary.details

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.books.tanga.common.ui.ShimmerSummaryItemSmall
import app.books.tanga.common.ui.ShimmerSummaryListRow
import app.books.tanga.core_ui.theme.LocalSpacing

@Composable
fun SummaryDetailsShimmerLoader() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "Summary list Shimmer transition")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = "Summary list Shimmer translate animation"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.medium)
    ) {
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))

        /* Start header zone (image + title + author) */
        Row {
            ShimmerSummaryItemSmall(modifier = Modifier, brush = brush)

            Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
            Column(
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(brush)
                )

                Spacer(modifier = Modifier.width(LocalSpacing.current.large))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(brush)
                )
            }
        }
        /* End header zone */

        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))

        /* Start introduction zone */
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        /* End introduction zone */

        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))

        /* Start recommendations zone */
        ShimmerSummaryListRow(brush)
        /* End recommendations zone */
    }
}