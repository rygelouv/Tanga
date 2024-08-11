package app.books.tanga.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.user.UserRepository
import app.books.tanga.feature.auth.AuthenticationInteractor
import app.books.tanga.revenuecat.RevenueCatPurchases
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.Events
import app.books.tanga.tracking.Pages
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthenticationInteractor,
    private val userRepository: UserRepository,
    private val revenueCatController: RevenueCatPurchases,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    private val _events: Channel<ProfileUiEvent> = Channel()
    val events: Flow<ProfileUiEvent> = _events.receiveAsFlow()

    init {
        analyticsTracker.trackPage(Pages.PROFILE)
        viewModelScope.launch {
            userRepository.getUserStream().collect {
                val user = it ?: return@collect
                val subscriberInfo = revenueCatController.getSubscriberInfo()
                _state.update { state ->
                    state.copy(
                        userInfo = UserInfoUi(
                            fullName = user.fullName,
                            photoUrl = user.photoUrl,
                            isAnonymous = user.isAnonymous,
                            subscriberInfo = subscriberInfo
                        )
                    )
                }
            }
        }
    }

    fun onPremiumUpgrade() {
        analyticsTracker.track(Events.TAP_TANGA_PREMIUM_UPGRADE)
        postEvent(ProfileUiEvent.NavigateTo.ToPricingPlan)
    }

    fun onLogin() {
        postEvent(ProfileUiEvent.NavigateTo.ToAuth)
    }

    @Deprecated("Logout has been moved to the settings screen. Need to remove this method.")
    fun onLogout() {
        viewModelScope.launch {
            authInteractor.signOut().onSuccess {
                analyticsTracker.track(Events.ACTION_USER_SIGNED_OUT)
            }
        }
    }

    private fun postEvent(event: ProfileUiEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
