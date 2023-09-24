package app.books.tanga.feature.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.domain.favorites.FavoriteInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val favoriteInteractor: FavoriteInteractor
): ViewModel() {

    private val _state: MutableStateFlow<LibraryUiState> =
        MutableStateFlow(LibraryUiState(progressState = ProgressState.Show))
    val state: StateFlow<LibraryUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeFavoritesChanges()
        }
    }

    /**
     * Observe the changes in the favorites collection and update the state accordingly
     */
    private suspend fun observeFavoritesChanges() {
        favoriteInteractor.observeFavorites().collect { favorites ->
            _state.update { state ->
                state.copy(
                    progressState = ProgressState.Hide,
                    favorites = favorites.map { it.toFavoriteUi() }
                )
            }
        }
    }
}