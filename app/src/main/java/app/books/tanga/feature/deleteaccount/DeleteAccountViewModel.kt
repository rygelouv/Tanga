package app.books.tanga.feature.deleteaccount

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
class DeleteAccountViewModel @Inject constructor(
    private val authInteractor: AuthenticationInteractor,
) : ViewModel() {

    private val _state: MutableStateFlow<DeleteAccountUiState> = MutableStateFlow(DeleteAccountUiState())
    val state: StateFlow<DeleteAccountUiState> = _state.asStateFlow()

    private val _events: Channel<DeleteAccountUiEvent> = Channel()
    val events: Flow<DeleteAccountUiEvent> = _events.receiveAsFlow()

    fun onDeleteAccount() {
        _state.value = _state.value.copy(showConfirmationDialog = true)
    }

    fun onDeleteConfirmAccount() {
        viewModelScope.launch {
            authInteractor.deleteAccount().onSuccess {
                _events.send(DeleteAccountUiEvent.NavigateTo.Auth)
            }
        }
    }

    fun onDismissConfirmationDialog() {
        _state.value = _state.value.copy(showConfirmationDialog = false)
    }
}
