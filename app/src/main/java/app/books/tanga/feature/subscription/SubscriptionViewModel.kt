package app.books.tanga.feature.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.SubscriptionType
import app.books.tanga.feature.protectedaction.ProtectedAction
import app.books.tanga.feature.protectedaction.ProtectedActionCheckResult
import app.books.tanga.feature.protectedaction.ProtectedActionInteractor
import app.books.tanga.revenuecat.RevenueCatPurchases
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.Events
import app.books.tanga.tracking.Pages
import app.books.tanga.tracking.Properties
import app.books.tanga.tracking.Property
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
    private val userRepository: UserRepository,
    private val protectedActionInteractor: ProtectedActionInteractor,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {

    private val _state = MutableStateFlow(PricingPlanUiState())
    val state: StateFlow<PricingPlanUiState> = _state.asStateFlow()

    private val _events: Channel<SubscriptionUiEvent> = Channel(Channel.BUFFERED)
    val events: Flow<SubscriptionUiEvent> = _events.receiveAsFlow()

    init {
        analyticsTracker.trackPage(Pages.SUBSCRIPTION)
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
    fun onSubscriptionPlanSelected(input: PurchaseSubscriptionInput) {
        viewModelScope.launch {
            val protectedActionCheckResult = protectedActionInteractor.checkProtectedAction(
                ProtectedAction.AuthRequiredAction.Subscribe
            )
            processProtectedActionCheckResult(protectedActionCheckResult) {
                _state.update {
                    it.copy(
                        monthlyPlanUi = it.monthlyPlanUi?.copy(selected = input.plan == it.monthlyPlanUi),
                        yearlyPlanUi = it.yearlyPlanUi?.copy(selected = input.plan == it.yearlyPlanUi)
                    )
                }
                trackTapSubscriptionEvent(input)

                val subscriptionPlan = _state.value.subscriptionPlans
                    ?.find { it.productId == input.plan.productId } ?: return@processProtectedActionCheckResult
                val params = RevenueCatPurchases.PurchaseParams(
                    context = input.context,
                    subscriptionPlan = subscriptionPlan
                )
                _state.update { it.copy(progressState = ProgressState.Show) }
                makeSubscriptionPurchase(params, input)
            }
        }
    }

    private fun processProtectedActionCheckResult(
        result: ProtectedActionCheckResult,
        action: () -> Unit
    ) {
        when (result) {
            ProtectedActionCheckResult.AuthRequired -> {
                _state.update { it.copy(showAuthSuggestion = true) }
            }
            ProtectedActionCheckResult.Allowed -> action()
            ProtectedActionCheckResult.SubscriptionRequired -> Unit // No-op
        }
    }

    private fun makeSubscriptionPurchase(
        params: RevenueCatPurchases.PurchaseParams,
        input: PurchaseSubscriptionInput
    ) {
        viewModelScope.launch {
            revenueCatController.purchase(params).onSuccess {
                trackSubscriptionPurchase(input)
                userRepository.getUser().onSuccess { user ->
                    if (user != null) {
                        val subscriberInfo = revenueCatController.getSubscriberInfo()
                        if (subscriberInfo?.hasActiveSubscription == true) {
                            userRepository.updateUser(user.copy(subscribedAt = Date()))
                            _events.send(SubscriptionUiEvent.SubscriptionPurchased)
                            _state.update { it.copy(progressState = ProgressState.Hide) }
                        }
                    }
                }
            }.onFailure {
                _state.update { it.copy(progressState = ProgressState.Hide) }
            }
        }
    }

    private fun trackTapSubscriptionEvent(input: PurchaseSubscriptionInput) {
        when (input.plan.productId) {
            state.value.monthlyPlanUi?.productId -> {
                analyticsTracker.track(
                    Events.TAP_MONTHLY_SUBSCRIPTION,
                    mapOf(Properties.SUBSCRIPTION_TYPE to SubscriptionType.MONTHLY)
                )
            }

            state.value.yearlyPlanUi?.productId -> {
                analyticsTracker.track(
                    Events.TAP_YEARLY_SUBSCRIPTION,
                    mapOf(Properties.SUBSCRIPTION_TYPE to SubscriptionType.YEARLY)
                )
            }
        }
    }

    private fun trackSubscriptionPurchase(input: PurchaseSubscriptionInput) {
        val subscriptionType = when (input.plan.productId) {
            state.value.monthlyPlanUi?.productId -> SubscriptionType.MONTHLY
            state.value.yearlyPlanUi?.productId -> SubscriptionType.YEARLY
            else -> null
        }
        val props = mutableMapOf<Property, Any>()
        props[Properties.SUBSCRIPTION_PRICE] = input.rawPrice
        subscriptionType?.let { props.put(Properties.SUBSCRIPTION_TYPE, it) }
        input.currency?.let { props.put(Properties.SUBSCRIPTION_CURRENCY, it) }
        analyticsTracker.track(Events.ACTION_SUBSCRIPTION_PURCHASED, props)
    }

    fun dismissAuthSuggestions() {
        _state.update { it.copy(showAuthSuggestion = false) }
    }
}
