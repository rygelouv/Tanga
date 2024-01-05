package app.books.tanga.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.library.FavoriteInteractor
import app.books.tanga.feature.summary.toSummaryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor,
    // This will be removed once the favorites are loaded on app start up
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState(progressState = ProgressState.Show))
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadHomeData()
        // This will be removed once the favorites are loaded on app start up
        loadFavoritesInBackground()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            loadUserInfo()
            loadWeeklySummary()
            loadSections()
        }
    }

    private fun loadWeeklySummary() {
        viewModelScope.launch {
            homeInteractor
                .getWeeklySummary()
                .onSuccess { summary ->
                    _state.update { it.copy(weeklySummary = summary.toSummaryUi()) }
                }.onFailure {
                    Timber.e(it, "HomeViewModel", "Error loading weekly summary")
                    _state.update { state ->
                        state.copy(error = it.toUiError())
                    }
                }
        }
    }

    private suspend fun loadSections() {
        homeInteractor
            .getSummarySections()
            .onSuccess { sections ->
                _state.update {
                    it.copy(
                        progressState = ProgressState.Hide,
                        sections =
                        sections.map { section ->
                            section.toHomeSectionUi()
                        }
                    )
                }
            }.onFailure {
                Timber.e(it, "HomeViewModel", "Error loading home sections")
                _state.update { state ->
                    state.copy(
                        progressState = ProgressState.Hide,
                        error = it.toUiError()
                    )
                }
            }
    }

    fun onRetry() {
        _state.update { it.copy(error = null, progressState = ProgressState.Show) }
        loadHomeData()
    }

    private suspend fun loadUserInfo() {
        homeInteractor
            .getUserInfo()
            .onSuccess { result ->
                val user = result ?: return@onSuccess
                _state.update {
                    it.copy(
                        userFirstName = user.firsName,
                        userPhotoUrl = user.photoUrl
                    )
                }
            }.onFailure {
                Timber.e(it, "HomeViewModel", "Error loading user info")
            }
    }

    private fun loadFavoritesInBackground() {
        viewModelScope.launch {
            favoriteInteractor
                .getFavorites()
                .onSuccess { favorites ->
                    Timber.i("HomeViewModel", "Favorites loaded: $favorites")
                }.onFailure {
                    Timber.e(it, "HomeViewModel", "Error loading favorites")
                }
        }
    }
}
