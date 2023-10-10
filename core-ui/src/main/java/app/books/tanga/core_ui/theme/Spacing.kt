package app.books.tanga.core_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A class to model spacing values for Tanga.
 *
 * @param extraSmall The extra small spacing value to be rendered.
 * @param small The small spacing value to be rendered.
 * @param medium The medium spacing value to be rendered.
 * @param large The large spacing value to be rendered.
 * @param extraLarge The extra large spacing value to be rendered.
 * @param extraExtraLarge The extra extra large spacing value to be rendered.
 */
@Immutable
data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
    val extraMediumLarge: Dp = 48.dp,
    val extraExtraLarge: Dp = 64.dp,
)

/**
 * A composition local for [Spacing].
 */
val LocalSpacing = staticCompositionLocalOf { Spacing() }
