package app.books.tanga.coreui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * A class to model gradient color values for Tanga.
 *
 * @param start The start gradient color to be rendered.
 * @param end The last gradient color to be rendered.
 * @param center The center gradient color to be rendered.
 */
@Immutable
data class GradientColors(
    val start: Color = Color.Unspecified,
    val center: Color = Color.Unspecified,
    val end: Color = Color.Unspecified
)

/**
 * A composition local for [GradientColors].
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColors() }
