package app.books.tanga.core_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class TintColor(
    val color: Color = Color.Unspecified
)

/**
 * A composition local for [TintColor].
 */
val LocalTintColor = staticCompositionLocalOf { TintColor() }
