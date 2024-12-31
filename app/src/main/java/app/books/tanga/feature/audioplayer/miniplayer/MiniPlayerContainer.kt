package app.books.tanga.feature.audioplayer.miniplayer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.audioplayer.fullplayer.MiniPlayerEvents
import app.books.tanga.feature.audioplayer.infrastructure.PlayerActions
import app.books.tanga.navigation.NavigationScreen

@Composable
fun MiniPlayerContainer(
    currentDestinationRoute: String,
    onExpendPlayer: (SummaryId) -> Unit,
    onStatusChange: (MiniPlayerStatus) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MiniPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(initialValue = null)
    val actions: PlayerActions = viewModel

    LaunchedEffect(key1 = state.summaryId) {
        viewModel.init()
    }

    HandleEvents(event = event, onStatusChange = onStatusChange)

    if (state.showMiniPlayer) {
        Column(modifier = modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))
            MiniPlayer(
                state = state,
                actions = actions,
                onExpandPlayer = onExpendPlayer,
                onDismiss = viewModel::onDismissMiniPlayer,
            )

            // Add space for bottom bar screens to avoid overlapping the bottom bar with the mini player
            if (currentDestinationRoute.isBottomBarScreen()) {
                Spacer(modifier = Modifier.height(76.dp))
            }
        }
    }
}

@Composable
fun HandleEvents(
    event: MiniPlayerEvents?,
    onStatusChange: (MiniPlayerStatus) -> Unit
) {
    when (event) {
        is MiniPlayerEvents.StatusChanged -> {
            onStatusChange(event.status)
        }
        null -> Unit
    }
}

fun String.isBottomBarScreen(): Boolean = this in listOf(
    NavigationScreen.BottomBarScreen.Home.route,
    NavigationScreen.BottomBarScreen.Library.route,
    NavigationScreen.BottomBarScreen.Profile.route
)
