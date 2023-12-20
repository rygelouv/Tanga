package app.books.tanga.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.user.UserRepository
import app.books.tanga.feature.auth.AuthenticationInteractor
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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    private val _events: Channel<ProfileUiEvent> = Channel()
    val events: Flow<ProfileUiEvent> = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            userRepository.getUser().onSuccess {
                val user = it ?: return@launch
                _state.update { state ->
                    state.copy(
                        userInfo = UserInfoUi(
                            fullName = user.fullName,
                            photoUrl = user.photoUrl,
                            isAnonymous = user.isAnonymous,
                        )
                    )
                }
            }
        }
    }

    fun onProUpgrade() {
        postEvent(ProfileUiEvent.NavigateTo.ToPricingPlan)
    }

    fun onLogin() {
        postEvent(ProfileUiEvent.NavigateTo.ToAuth)
    }

    fun onLogout() {
        viewModelScope.launch {
            authInteractor.signOut().onSuccess {}
        }
    }

    private fun postEvent(event: ProfileUiEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
