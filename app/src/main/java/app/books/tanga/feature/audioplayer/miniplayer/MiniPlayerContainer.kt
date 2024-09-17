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
import app.books.tanga.feature.audioplayer.infrastructure.PlayerActions
import app.books.tanga.navigation.NavigationScreen

@Composable
fun MiniPlayerContainer(
    currentDestinationRoute: String,
    onExpendPlayer: (SummaryId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MiniPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val actions: PlayerActions = viewModel

    LaunchedEffect(key1 = state.summaryId) {
        viewModel.init()
    }

    if (state.showMiniPlayer) {
        Column(modifier = modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))
            MiniPlayer(
                state = state,
                actions = actions,
                onExpandPlayer = onExpendPlayer,
                onDismiss = {
                    viewModel.onDismissMiniPlayer()
                }
            )

            if (currentDestinationRoute.isBottomBarScreen()) {
                Spacer(modifier = Modifier.height(76.dp))
            }
        }
    }
}

fun String.isBottomBarScreen(): Boolean = this in listOf(
    NavigationScreen.BottomBarScreen.Home.route,
    NavigationScreen.BottomBarScreen.Library.route,
    NavigationScreen.BottomBarScreen.Profile.route
)
