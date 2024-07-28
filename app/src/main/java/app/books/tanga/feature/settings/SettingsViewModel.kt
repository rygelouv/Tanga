package app.books.tanga.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.feature.auth.AuthenticationInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authInteractor: AuthenticationInteractor
) : ViewModel() {
    private val _state: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    private val _events: Channel<SettingsUiEvent> = Channel()
    val events: Flow<SettingsUiEvent> = _events.receiveAsFlow()

    fun onLogout() {
        _state.value = _state.value.copy(showLogoutConfirmationDialog = true)
    }

    fun onConfirmLogout() {
        _state.value = _state.value.copy(showLogoutConfirmationDialog = false, isLoggingOut = true)
        viewModelScope.launch {
            authInteractor.signOut().onSuccess {
                _state.value = _state.value.copy(isLoggingOut = false)
                postEvent(SettingsUiEvent.NavigateTo.Auth)
            }
        }
    }

    fun onDismissLogoutConfirmationDialog() {
        _state.value = _state.value.copy(showLogoutConfirmationDialog = false)
    }

    private fun postEvent(event: SettingsUiEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
