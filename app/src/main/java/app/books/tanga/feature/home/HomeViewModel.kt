package app.books.tanga.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState(isLoading = true))
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        startFakeLoading()
    }

    private fun startFakeLoading() {
        viewModelScope.launch {
            userRepository.getUser().onSuccess { result ->
                val user = result ?: return@onSuccess
                _state.update {
                    it.copy(
                        userFirstName = user.firsName,
                        userPhotoUrl = user.photoUrl
                    )
                }
            }
            withContext(Dispatchers.IO) { delay(2000) }
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }
}