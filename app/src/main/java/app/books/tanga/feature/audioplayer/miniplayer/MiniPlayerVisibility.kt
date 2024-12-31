package app.books.tanga.feature.audioplayer.miniplayer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val MINI_PLAYER_HEIGHT = 82 // Default height in dp

/**
 * Data class to hold mini player visibility state
 */
data class MiniPlayerState(
    val status: MiniPlayerStatus = MiniPlayerStatus.NOT_READY,
    val height: Int = MINI_PLAYER_HEIGHT
)

enum class MiniPlayerStatus {
    NOT_READY,
    VISIBLE,
    HIDDEN;

    fun isVisible() = this == VISIBLE
}

/**
 * Composition local to provide mini player state
 */
val LocalMiniPlayerState = staticCompositionLocalOf {
    MiniPlayerState()
}

/**
 * Composable function to add spacer when mini player is visible
 */
@Composable
fun MiniPlayerAwareSpacer(
    modifier: Modifier = Modifier
) {
    val miniPlayerState = LocalMiniPlayerState.current
    Spacer(
        modifier = modifier.height(
            if (miniPlayerState.status.isVisible()) {
                miniPlayerState.height.dp
            } else {
                0.dp
            }
        )
    )
}

/**
 * Composable function to provide mini player state.
 * This function should be used at the top level of the composable tree where mini player is used.
 */
@Composable
fun ProvideMiniPlayerState(
    miniPlayerState: MiniPlayerState,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalMiniPlayerState provides miniPlayerState
    ) {
        content()
    }
}
