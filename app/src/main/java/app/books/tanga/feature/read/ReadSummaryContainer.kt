package app.books.tanga.feature.read

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.SummaryId

@Composable
fun ReadSummaryScreenContainer(
    summaryId: SummaryId,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToPricingPlans: () -> Unit,
    viewModel: ReadSummaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(ReadSummaryUiEvent.Empty)

    LaunchedEffect(key1 = summaryId) {
        viewModel.loadSummary(summaryId)
    }

    HandleEvents(
        event = event,
        onNavigateToAudioPlayer = onNavigateToAudioPlayer,
        onNavigateToPricingPlans = onNavigateToPricingPlans
    )

    ReadSummaryScreen(
        state = state,
        onNavigateToPreviousScreen = onNavigateToPreviousScreen,
        onNavigateToAudioPlayer = { viewModel.onPlayAudioClicked() },
        onToggleFavorite = { viewModel.toggleFavorite() },
        onFontSizeClick = { viewModel.onFontSizeClicked() },
        onFontScaleChange = { viewModel.onScaleChanged(it) },
    )
}

@Composable
fun HandleEvents(
    event: ReadSummaryUiEvent,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToPricingPlans: () -> Unit
) {
    when (event) {
        is ReadSummaryUiEvent.NavigateTo.ToAudioPlayer -> {
            onNavigateToAudioPlayer(event.summaryId)
        }

        is ReadSummaryUiEvent.NavigateTo.ToPricingPlans -> {
            onNavigateToPricingPlans()
        }

        else -> Unit
    }
}
