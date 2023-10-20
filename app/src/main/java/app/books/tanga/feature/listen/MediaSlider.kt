package app.books.tanga.feature.listen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.books.tanga.feature.audioplayer.PlaybackState

/**
 * A slider that allows the user to change the current position of the currently playing media.
 */
@Composable
fun MediaSlider(
    playbackState: PlaybackState?,
    modifier: Modifier = Modifier,
    onSliderPositionChange: (Long) -> Unit
) {
    // State variable to track whether the user is currently dragging the slider.
    var isDragging by remember { mutableStateOf(false) }

    // State variable to hold the value while the user is dragging.
    var dragValue by remember { mutableFloatStateOf(0f) }

    // Determine the current value for the slider, based on whether it's being dragged or not.
    val sliderValue: Float =
        if (isDragging) {
            dragValue
        } else {
            playbackState?.position?.toFloat() ?: 0f
        }

    Slider(
        modifier = modifier.fillMaxWidth(),
        value = sliderValue,
        onValueChange = {
            // Update the state to indicate the slider is being dragged and store the current drag value.
            isDragging = true
            dragValue = it
        },
        onValueChangeFinished = {
            // Once the user stops dragging, update the playback position and reset the dragging state.
            isDragging = false
            onSliderPositionChange(dragValue.toLong())
        },
        valueRange = 0f..(playbackState?.duration?.toFloat() ?: 0f)
    )
}
