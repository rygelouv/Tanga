package app.books.tanga.coreui.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class TintColor(
    val color: Color = Color.Unspecified,
    val disabled: Color = Color.Unspecified
)

/**
 * A composition local for [TintColor].
 * See the reason for SuppressLint here: https://developer.android.com/jetpack/compose/compositionlocal#deciding
 */
@SuppressLint("ComposeCompositionLocalUsage")
val LocalTintColor = staticCompositionLocalOf { TintColor() }
