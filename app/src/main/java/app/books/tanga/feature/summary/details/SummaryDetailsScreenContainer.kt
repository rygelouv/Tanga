package app.books.tanga.feature.summary.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.auth.AuthSuggestionBottomSheet

@Composable
fun SummaryDetailsScreenContainer(
    summaryId: SummaryId,
    onNavigateToAuth: () -> Unit,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToReadSummaryScreen: (SummaryId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SummaryDetailsViewModel = hiltViewModel(),
    onNavigateToRecommendedSummaryDetails: (SummaryId) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadSummary(summaryId)
    }

    val event by viewModel.events.collectAsStateWithLifecycle(initialValue = SummaryDetailsUiEvent.Empty)
    HandleEvents(
        event = event,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToPreviousScreen = onNavigateToPreviousScreen,
        onNavigateToAudioPlayer = onNavigateToAudioPlayer,
        onNavigateToReadSummaryScreen = onNavigateToReadSummaryScreen,
        onNavigateToRecommendedSummaryDetails = onNavigateToRecommendedSummaryDetails
    )

    SummaryDetailsScreen(
        state = state,
        modifier = modifier,
        onBackClick = onNavigateToPreviousScreen,
        onPlayClick = { viewModel.onPlayClick() },
        onReadClick = { viewModel.onReadClick() },
        onLoadSummary = { viewModel.loadSummary(it) },
        onToggleFavorite = { viewModel.toggleFavorite() },
        onRecommendationClick = onNavigateToRecommendedSummaryDetails
    )
}

@Composable
fun HandleEvents(
    event: SummaryDetailsUiEvent,
    onNavigateToAuth: () -> Unit,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToReadSummaryScreen: (SummaryId) -> Unit,
    onNavigateToRecommendedSummaryDetails: (SummaryId) -> Unit
) {
    var showAuthSuggestion by rememberSaveable {
        mutableStateOf(false)
    }

    if (showAuthSuggestion) {
        AuthSuggestionBottomSheet(
            onDismiss = { showAuthSuggestion = false }
        )
    }

    when (event) {
        is SummaryDetailsUiEvent.NavigateTo.ToAuth -> {
            LaunchedEffect(Unit) {
                onNavigateToAuth()
            }
        }

        is SummaryDetailsUiEvent.NavigateTo.ToPrevious -> {
            LaunchedEffect(Unit) {
                onNavigateToPreviousScreen()
            }
        }

        is SummaryDetailsUiEvent.NavigateTo.ToAudioPlayer -> {
            LaunchedEffect(Unit) {
                onNavigateToAudioPlayer(event.summaryId)
            }
        }

        is SummaryDetailsUiEvent.NavigateTo.ToReadSummary -> {
            LaunchedEffect(Unit) {
                onNavigateToReadSummaryScreen(event.summaryId)
            }
        }

        is SummaryDetailsUiEvent.NavigateTo.ToSummaryDetails -> {
            LaunchedEffect(Unit) {
                onNavigateToRecommendedSummaryDetails(event.summaryId)
            }
        }

        is SummaryDetailsUiEvent.ShowAuthSuggestion -> {
            LaunchedEffect(event.id) {
                showAuthSuggestion = true
            }
        }

        else -> Unit
    }
}
