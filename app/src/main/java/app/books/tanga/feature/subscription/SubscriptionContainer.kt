package app.books.tanga.feature.subscription

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.feature.auth.AuthSuggestionBottomSheet

@Composable
fun SubscriptionContainer(
    onCloseClick: () -> Unit,
    onNavigateToAuth: () -> Unit,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val events by viewModel.events.collectAsStateWithLifecycle(initialValue = SubscriptionUiEvent.Empty)
    SubscriptionScreen(
        onCloseClick = onCloseClick,
        onPlanSelect = viewModel::onSubscriptionPlanSelected,
        state = state
    )
    HandleEvents(events, onCloseClick)
    if (state.showAuthSuggestion) {
        AuthSuggestionBottomSheet(
            onDismiss = { viewModel.dismissAuthSuggestions() },
            onNavigateToAuth = {
                viewModel.dismissAuthSuggestions()
                onNavigateToAuth()
            }
        )
    }
}

@Composable
fun HandleEvents(events: SubscriptionUiEvent, onSubscriptionPurchase: () -> Unit) {
    when (events) {
        is SubscriptionUiEvent.SubscriptionPurchased -> onSubscriptionPurchase()
        else -> Unit
    }
}
