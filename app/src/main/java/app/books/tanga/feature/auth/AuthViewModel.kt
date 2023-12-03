package app.books.tanga.feature.auth

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.TangaErrorTracker
import app.books.tanga.errors.toUiError
import com.google.android.gms.auth.api.identity.SignInClient
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
import timber.log.Timber

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthenticationInteractor,
    private val signInClient: SignInClient,
    private val errorTracker: TangaErrorTracker
) : ViewModel() {
    private val _state: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    private val _events: Channel<AuthUiEvent> = Channel()
    val events: Flow<AuthUiEvent> = _events.receiveAsFlow()

    fun onGoogleSignInStarted() {
        _state.update { it.copy(googleSignInButtonProgressState = ProgressState.Show) }
        viewModelScope.launch {
            interactor
                .initGoogleSignIn()
                .onSuccess { initSignInResult ->
                    postEvent(AuthUiEvent.LaunchGoogleSignIn(signInResult = initSignInResult))
                }.onFailure { error ->
                    Timber.e("Init Google sign In failure", error)
                    _state.update { it.copy(googleSignInButtonProgressState = ProgressState.Hide) }
                    postEvent(AuthUiEvent.Error(error.toUiError()))
                }
        }
    }

    fun onSkipAuth() {
        _state.update { it.copy(skipProgressState = ProgressState.Show) }
        viewModelScope.launch {
            interactor.signInAnonymously()
                .onSuccess { user ->
                    postEvent(AuthUiEvent.NavigateTo.ToHomeScreen)
                    errorTracker.setUserDetails(
                        userId = user.id,
                        userCreationDate = user.createdAt
                    )
                }.onFailure { error ->
                    _state.update { it.copy(skipProgressState = ProgressState.Hide) }
                    Timber.e("Sign In Anonymously failure: ${error.message}", error)
                    postEvent(AuthUiEvent.Error(error.toUiError()))
                }
        }
    }

    fun onGoogleSignInCompleted(intent: Intent) {
        viewModelScope.launch {
            val credentials = signInClient.getSignInCredentialFromIntent(intent)
            interactor
                .completeGoogleSignIn(credentials)
                .onSuccess { user ->
                    postEvent(AuthUiEvent.NavigateTo.ToHomeScreen)
                    _state.update { it.copy(googleSignInButtonProgressState = ProgressState.Hide) }
                    errorTracker.setUserDetails(
                        userId = user.id,
                        userCreationDate = user.createdAt
                    )
                }.onFailure { error ->
                    Timber.e("Complete Google sign In failure", error)
                    _state.update { it.copy(googleSignInButtonProgressState = ProgressState.Hide) }
                    postEvent(AuthUiEvent.Error(error.toUiError()))
                }
        }
    }

    private fun postEvent(event: AuthUiEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
