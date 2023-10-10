package app.books.tanga.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.user.UserRepository
import app.books.tanga.feature.auth.AuthenticationInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
        viewModelScope.launch {
            userRepository.getUser().onSuccess {
                val user = it ?: return@launch
                _state.update { state ->
                    state.copy(
                        fullName = user.fullName,
                        photoUrl = user.photoUrl
                    )
                }
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            authInteractor.signOut().onSuccess {}
        }
    }
}
