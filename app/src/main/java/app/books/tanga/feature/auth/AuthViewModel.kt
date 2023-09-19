package app.books.tanga.feature.auth

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.domain.auth.AuthenticationInteractor
import app.books.tanga.domain.session.SessionState
import app.books.tanga.common.ui.ProgressIndicatorState
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthenticationInteractor,
    private val signInClient: SignInClient,
) : ViewModel() {

    private val _state: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.emptyState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    private val _events: MutableSharedFlow<AuthUiEvent> = MutableSharedFlow()
    val events: SharedFlow<AuthUiEvent> = _events.asSharedFlow()

    fun onGoogleSignInStarted() {
        _state.update { it.copy(googleSignInButtonProgressIndicatorState = ProgressIndicatorState.Show) }
        viewModelScope.launch {
            interactor.initGoogleSignIn()
                .onSuccess { initSignInResult ->
                    postEvent(AuthUiEvent.LaunchGoogleSignIn(signInResult = initSignInResult))
                }.onFailure {
                    Log.e("AuthViewModel", "Init sign In failure: ${it.message}")
                    // TODO (Properly track failure )
                    // TODO show failure UI with Retry
                }
        }
    }

    fun onGoogleSignInCompleted(intent: Intent) {
        viewModelScope.launch {
            val credentials = signInClient.getSignInCredentialFromIntent(intent)
            interactor.launchGoogleSignIn(credentials)
                .onSuccess { sessionStatus ->
                    if (sessionStatus is SessionState.LoggedIn) {
                        postEvent(AuthUiEvent.NavigateTo.ToHomeScreen)
                        _state.update { it.copy(googleSignInButtonProgressIndicatorState = ProgressIndicatorState.Hide) }
                    }
                }.onFailure { error ->
                    _state.update { it.copy(googleSignInButtonProgressIndicatorState = ProgressIndicatorState.Hide) }
                    Log.e("AuthViewModel", "Google sign In failure: ${error.message}")
                    // TODO (Properly track failure )
                    // TODO show failure UI with Retry
                }
        }
    }

    private fun postEvent(event: AuthUiEvent) {
        viewModelScope.launch { _events.emit(event) }
    }
}