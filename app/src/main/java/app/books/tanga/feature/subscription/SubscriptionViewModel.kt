package app.books.tanga.feature.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.SubscriptionType
import app.books.tanga.revenuecat.RevenueCatPurchases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val revenueCatController: RevenueCatPurchases,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PricingPlanUiState())
    val state: StateFlow<PricingPlanUiState> = _state.asStateFlow()

    private val _events: Channel<PricingPlanUiEvent> = Channel(Channel.BUFFERED)
    val events: Flow<PricingPlanUiEvent> = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            revenueCatController.getSubscriptions().onSuccess { subscriptionPlans ->
                _state.update { uiState ->
                    uiState.copy(
                        subscriptionPlans = subscriptionPlans,
                        monthlyPlanUi = subscriptionPlans.find { it.type == SubscriptionType.MONTHLY }?.toUi(),
                        yearlyPlanUi = subscriptionPlans.find { it.type == SubscriptionType.YEARLY }?.toUi()
                    )
                }
            }.onFailure {
                Timber.e(it, "Failed to get subscription plans")
            }
        }
    }

    /**
     * Called when the user selects a subscription plan.
     * - Indicates the selected plan in the UI.
     * - Creates the purchase parameters and initiates the purchase process.
     * - When the purchase is successful, get the user subscription info and updates the user's subscription
     * status (date) in the database.
     */
    fun onPlanSelected(input: PurchaseSubscriptionInput) {
        _state.update {
            it.copy(
                monthlyPlanUi = it.monthlyPlanUi?.copy(selected = input.plan == it.monthlyPlanUi),
                yearlyPlanUi = it.yearlyPlanUi?.copy(selected = input.plan == it.yearlyPlanUi)
            )
        }

        viewModelScope.launch {
            val subscriptionPlan = _state.value.subscriptionPlans
                ?.find { it.productId == input.plan.productId } ?: return@launch
            val params = RevenueCatPurchases.PurchaseParams(
                context = input.context,
                subscriptionPlan = subscriptionPlan
            )
            _state.update { it.copy(progressState = ProgressState.Show) }
            revenueCatController.purchase(params).onSuccess {
                userRepository.getUser().onSuccess { user ->
                    if (user != null) {
                        val subscriberInfo = revenueCatController.getSubscriberInfo()
                        Timber.d("Subscriber info: $subscriberInfo")
                        if (subscriberInfo?.hasActiveSubscription == true) {
                            userRepository.updateUser(user.copy(subscribedAt = Date()))
                            _events.send(PricingPlanUiEvent.SubscriptionPurchased)
                            _state.update { it.copy(progressState = ProgressState.Hide) }
                        }
                    }
                }
            }.onFailure {
                _state.update { it.copy(progressState = ProgressState.Hide) }
            }
        }
    }
}
