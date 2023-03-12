package app.books.tanga.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.data.UserRepository
import app.books.tanga.common.domain.auth.AuthenticationInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthenticationInteractor,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    init {
        _state.update { it.copy(fullName = "Rygel Louv") }
    }

    fun onLogout() {
        viewModelScope.launch {
            authInteractor.signOut().onSuccess {

                }
        }
    }
}