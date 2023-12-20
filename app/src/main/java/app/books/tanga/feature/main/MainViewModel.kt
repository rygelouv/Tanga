package app.books.tanga.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.feature.auth.AuthenticationInteractor
import app.books.tanga.session.SessionManager
import app.books.tanga.session.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authInteractor: AuthenticationInteractor
) : ViewModel() {
    private val _event: MutableSharedFlow<MainUiEvent> = MutableSharedFlow()
    val event: SharedFlow<MainUiEvent> = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            sessionManager
                .sessionState()
                .filterIsInstance<SessionState.SignedOut>()
                .collect {
                    if (authInteractor.isUserAnonymous().not()) {
                        postNavigateToAuthEvent()
                    }
                }
        }
    }

    private suspend fun postNavigateToAuthEvent() {
        _event.emit(MainUiEvent.NavigateTo.ToAuth)
    }
}
