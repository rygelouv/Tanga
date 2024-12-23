package app.books.tanga.feature.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.feature.auth.AuthenticationInteractor
import app.books.tanga.navigation.NavigationScreen
import app.books.tanga.session.SessionManager
import app.books.tanga.session.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: DefaultPrefDataStoreRepository,
    private val sessionManager: SessionManager,
    private val authenticationInteractor: AuthenticationInteractor
) : ViewModel() {
    private val _state: MutableState<SplashState> = mutableStateOf(SplashState())
    val state: State<SplashState> = _state

    init {
        viewModelScope.launch {
            val onboardingCompletionState = repository.getOnboardingCompletionState()
            if (onboardingCompletionState) {
                when (sessionManager.sessionState().first()) {
                    SessionState.SignedOut -> {
                        if (authenticationInteractor.isUserAnonymous()) {
                            _state.value = SplashState(false, NavigationScreen.Main)
                        } else {
                            _state.value = SplashState(false, NavigationScreen.Authentication)
                        }
                    }

                    is SessionState.SignedIn -> {
                        _state.value = SplashState(false, NavigationScreen.Main)
                    }
                }
            } else {
                _state.value = SplashState(false, NavigationScreen.Landing)
            }
        }
    }
}
