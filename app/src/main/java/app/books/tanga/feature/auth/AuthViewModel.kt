package app.books.tanga.feature.auth

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.domain.AuthenticationInteractor
import app.books.tanga.common.ui.ProgressState
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthenticationInteractor,
    private val signInClient: SignInClient,
) : ViewModel() {

    private val _events: MutableSharedFlow<AuthUiEvent> = MutableSharedFlow()
    val events: MutableSharedFlow<AuthUiEvent> by ::_events

    fun onGoogleSignInStarted() {
        postEvent(AuthUiEvent.ShowProgress(progressState = ProgressState.Loading))
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
                .onSuccess {
                    postEvent(AuthUiEvent.NavigateTo.ToHoMeScreen)
                }.onFailure {
                    Log.e("AuthViewModel", "Google sign In failure: ${it.message}")
                    // TODO (Properly track failure )
                    // TODO show failure UI with Retry
                }
        }
    }

    private fun postEvent(event: AuthUiEvent) {
        viewModelScope.launch { events.emit(event) }
    }
}