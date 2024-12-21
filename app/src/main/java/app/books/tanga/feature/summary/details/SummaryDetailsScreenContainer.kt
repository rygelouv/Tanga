package app.books.tanga.feature.summary.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.auth.AuthSuggestionBottomSheet
import app.books.tanga.utils.openLink
import app.books.tanga.utils.shareSummary

const val BASE_WEB_URL = "https://tanga-web-app--tanga-d571c.us-central1.hosted.app/summary-details/"

@Composable
fun SummaryDetailsScreenContainer(
    summaryId: SummaryId,
    onNavigateToAuth: () -> Unit,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToSubscriptions: () -> Unit,
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
        onNavigateToSubscriptions = onNavigateToSubscriptions,
        onNavigateToAudioPlayer = onNavigateToAudioPlayer,
        onNavigateToReadSummaryScreen = onNavigateToReadSummaryScreen,
        onNavigateToRecommendedSummaryDetails = onNavigateToRecommendedSummaryDetails
    )
    val context = LocalContext.current
    SummaryDetailsScreen(
        state = state,
        modifier = modifier,
        onBackClick = onNavigateToPreviousScreen,
        onPlayAudioClick = { viewModel.onPlayClick() },
        onReadClick = { viewModel.onReadClick() },
        onLoadSummary = { viewModel.loadSummary(it) },
        onToggleFavorite = { viewModel.onToggleFavorite() },
        onRecommendationClick = { id ->
            viewModel.onRecommendationClick(id)
            onNavigateToRecommendedSummaryDetails(id)
        },
        onShare = {
            viewModel.onShare(it.id)
            shareSummary(
                context = context,
                summaryTitle = it.title,
                summaryAuthor = it.author,
                url = BASE_WEB_URL + it.id.value
            )
        },
        onPurchase = {
            viewModel.onPurchase(it.id)
            it.purchaseBookUrl?.let { url -> openLink(context = context, url = url) }
        }
    )
}

@Composable
fun HandleEvents(
    event: SummaryDetailsUiEvent,
    onNavigateToAuth: () -> Unit,
    onNavigateToSubscriptions: () -> Unit,
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
            onDismiss = { showAuthSuggestion = false },
            onNavigateToAuth = {
                showAuthSuggestion = false
                onNavigateToAuth()
            }
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

        is SummaryDetailsUiEvent.NavigateTo.ToSubscription -> {
            LaunchedEffect(Unit) {
                onNavigateToSubscriptions()
            }
        }

        else -> Unit
    }
}
