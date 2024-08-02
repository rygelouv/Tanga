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
    val events by viewModel.events.collectAsStateWithLifecycle(initialValue = PricingPlanUiEvent.Empty)
    SubscriptionScreen(
        onCloseClick = onCloseClick,
        onPlanSelect = viewModel::onPlanSelected,
        state = state
    )
    HandleEvents(events, onCloseClick)
}

@Composable
fun HandleEvents(events: PricingPlanUiEvent, onSubscriptionPurchase: () -> Unit) {
    when (events) {
        is PricingPlanUiEvent.SubscriptionPurchased -> onSubscriptionPurchase()
        else -> Unit
    }
}
