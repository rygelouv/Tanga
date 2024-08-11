package app.books.tanga.feature.subscription

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SubscriptionContainer(
    onCloseClick: () -> Unit,
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
}

@Composable
fun HandleEvents(events: SubscriptionUiEvent, onSubscriptionPurchase: () -> Unit) {
    when (events) {
        is SubscriptionUiEvent.SubscriptionPurchased -> onSubscriptionPurchase()
        else -> Unit
    }
}
