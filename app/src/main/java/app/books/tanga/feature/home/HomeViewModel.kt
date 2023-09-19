package app.books.tanga.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.UserRepository
import app.books.tanga.domain.summary.SummaryInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val summaryInteractor: SummaryInteractor
) : ViewModel() {
    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            loadUserInfo()

            loadSections()
        }
    }

    private suspend fun loadSections() {
        summaryInteractor.getSummarySections()
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
            }
    }

    private suspend fun loadUserInfo() {
        userRepository.getUser().onSuccess { result ->
            val user = result ?: return@onSuccess
            _state.update {
                it.copy(
                    userFirstName = user.firsName,
                    userPhotoUrl = user.photoUrl
                )
            }
        }
    }
}