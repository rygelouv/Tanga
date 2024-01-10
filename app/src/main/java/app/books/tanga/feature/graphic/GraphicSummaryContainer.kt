package app.books.tanga.feature.graphic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.SummaryId

@Composable
fun GraphicSummaryScreenContainer(
    summaryId: SummaryId,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToPricingPlans: () -> Unit,
    viewModel: GraphicSummaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(GraphicSummaryUiEvent.Empty)

    LaunchedEffect(key1 = summaryId) {
        viewModel.loadSummary(summaryId)
    }

    HandleEvents(
        event = event,
        onNavigateToAudioPlayer = onNavigateToAudioPlayer,
        onNavigateToPricingPlans = onNavigateToPricingPlans
    )

    GraphicSummaryScreen(
        state = state,
        onNavigateToPreviousScreen = onNavigateToPreviousScreen,
        onNavigateToAudioPlayer = { viewModel.onPlayAudioClicked() },
        onToggleFavorite = { viewModel.toggleFavorite() },
    )
}

@Composable
fun HandleEvents(
    event: GraphicSummaryUiEvent,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToPricingPlans: () -> Unit
) {
    when (event) {
        is GraphicSummaryUiEvent.NavigateTo.ToAudioPlayer -> {
            onNavigateToAudioPlayer(event.summaryId)
        }

        is GraphicSummaryUiEvent.NavigateTo.ToPricingPlans -> {
            onNavigateToPricingPlans()
        }

        else -> Unit
    }
}
