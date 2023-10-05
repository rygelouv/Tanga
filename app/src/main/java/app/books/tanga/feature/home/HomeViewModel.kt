package app.books.tanga.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.library.FavoriteInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor,
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadHomeData()
        loadFavoritesInBackground()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            loadUserInfo()
            loadSections()
        }
    }

    private suspend fun loadSections() {
        homeInteractor.getSummarySections()
            .onSuccess { sections ->
                _state.update {
                    it.copy(
                        sections = sections.map { section ->
                            section.toHomeSectionUi()
                        }
                    )
                }
            }.onFailure {
                Log.e("HomeViewModel", "Error loading home sections", it)
                _state.update { state ->
                    state.copy(error = it.toUiError())
                }
            }
    }

    private suspend fun loadUserInfo() {
        homeInteractor.getUserInfo().onSuccess { result ->
            val user = result ?: return@onSuccess
            _state.update {
                it.copy(
                    userFirstName = user.firsName,
                    userPhotoUrl = user.photoUrl
                )
            }
        }.onFailure {
            Log.e("HomeViewModel", "Error loading user info", it)
            _state.update { state ->
                state.copy(error = it.toUiError())
            }
        }
    }

    private fun loadFavoritesInBackground() {
        viewModelScope.launch {
            favoriteInteractor.getFavorites()
                .onSuccess { favorites ->
                    Log.i("HomeViewModel", "Favorites loaded: $favorites")
                }.onFailure {
                    Log.e("HomeViewModel", "Error loading favorites", it)
                }
        }
    }
}