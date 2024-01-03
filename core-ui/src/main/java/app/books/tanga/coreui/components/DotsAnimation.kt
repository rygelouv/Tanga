package app.books.tanga.coreui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Suppress("MagicNumber")
@Composable
fun DotsAnimation(
    modifier: Modifier = Modifier,
    animationDuration: Int = 300,
    numberOfDots: Int = 3
) {
    // Create an infinite transition that repeats the animation
    val infiniteTransition = rememberInfiniteTransition(label = "typing_indicator")
    val dotAnimations = List(numberOfDots) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration * numberOfDots
                    0f at animationDuration * index with LinearOutSlowInEasing
                    1f at animationDuration * index + animationDuration / 2 with LinearOutSlowInEasing
                },
                repeatMode = RepeatMode.Reverse
            ),
            label = "typing_indicator"
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(start = 10.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            dotAnimations.forEach { anim ->
                val scaleValue = 1f + anim.value * 0.5f
                Dot(scaleValue)
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

@Composable
private fun Dot(scaleValue: Float) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .scale(scaleValue)
            .background(MaterialTheme.colorScheme.tertiary, CircleShape)
    )
}
