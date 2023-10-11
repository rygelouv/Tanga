package app.books.tanga.feature.auth

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.TangaErrorTracker
import app.books.tanga.errors.toUiError
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthenticationInteractor,
    private val signInClient: SignInClient,
    private val errorTracker: TangaErrorTracker
) : ViewModel() {
    private val _state: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.emptyState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    private val _events: MutableSharedFlow<AuthUiEvent> = MutableSharedFlow()
    val events: SharedFlow<AuthUiEvent> = _events.asSharedFlow()

    fun onGoogleSignInStarted() {
        _state.update { it.copy(googleSignInButtonProgressState = ProgressState.Show) }
        viewModelScope.launch {
            interactor
                .initGoogleSignIn()
                .onSuccess { initSignInResult ->
                    postEvent(AuthUiEvent.LaunchGoogleSignIn(signInResult = initSignInResult))
                }.onFailure { error ->
                    Log.e("AuthViewModel", "Init Google sign In failure: ${error.message}")
                    _state.update { it.copy(googleSignInButtonProgressState = ProgressState.Hide) }
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
                    Log.e("AuthViewModel", "Complete Google sign In failure: ${error.message}")
                    _state.update { it.copy(googleSignInButtonProgressState = ProgressState.Hide) }
                    postEvent(AuthUiEvent.Error(error.toUiError()))
                }
        }
    }

    private fun postEvent(event: AuthUiEvent) {
        viewModelScope.launch { _events.emit(event) }
    }
}
