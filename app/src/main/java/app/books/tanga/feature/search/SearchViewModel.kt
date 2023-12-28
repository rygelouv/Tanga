package app.books.tanga.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.summary.SummaryInteractor
import app.books.tanga.feature.summary.toSummaryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val summaryInteractor: SummaryInteractor
) : ViewModel() {
    private val _state: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState(progressState = ProgressState.Show))
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private val _events: Channel<SearchUiEvent> = Channel()
    val events: Flow<SearchUiEvent> = _events.receiveAsFlow()

    init {
        loadSearchData()
    }

    private fun loadSearchData() {
        viewModelScope.launch {
            _state.update { it.copy(progressState = ProgressState.Show) }

            loadCategories()
            loadSummaries()
        }
    }

    private suspend fun loadCategories() {
        summaryInteractor
            .getCategories()
            .onSuccess { categories ->
                _state.update {
                    it.copy(
                        categories =
                        categories.map { category ->
                            category.toCategoryUi()
                        }
                    )
                }
            }.onFailure { error ->
                Timber.e(error, "SearchViewModel", "Error loading categories")
                postEvent(SearchUiEvent.ShowSnackError(error.toUiError()))
            }
    }

    private suspend fun loadSummaries() {
        summaryInteractor
            .getAllSummaries()
            .onSuccess { summaries ->
                _state.update {
                    it.copy(
                        progressState = ProgressState.Hide,
                        summaries =
                        summaries.map { summary ->
                            summary.toSummaryUi()
                        }
                    )
                }
            }.onFailure { error ->
                Timber.e(error, "SearchViewModel", "Error loading summaries")
                _state.update {
                    it.copy(
                        progressState = ProgressState.Hide,
                        error = error.toUiError()
                    )
                }
            }
    }

    fun onRetry() {
        _state.update { it.copy(error = null, progressState = ProgressState.Show) }
        loadSearchData()
    }

    fun onSearch(query: String) {
        // Only show categories if the query is empty
        _state.update {
            it.copy(
                progressState = ProgressState.Show,
                query = query,
                shouldShowCategories = query.isEmpty()
            )
        }

        viewModelScope.launch {
            summaryInteractor
                .search(query)
                .onSuccess { summaries ->
                    _state.update {
                        it.copy(
                            progressState = ProgressState.Hide,
                            summaries =
                            summaries.map { summary ->
                                summary.toSummaryUi()
                            }
                        )
                    }
                }.onFailure {
                    Timber.e(it, "SearchViewModel", "Error searching summaries")
                }
        }
    }

    /**
     * When a category is selected, we need add the category to the selected categories list
     * and load the summaries for the selected categories
     */
    fun onCategorySelected(category: CategoryUi) {
        updateSelectedCategoriesAndLoadSummaries { it.add(category) }
    }

    /**
     * When a category is deselected, we need remove the category from the selected categories list
     * and load the summaries for the selected categories
     */
    fun onCategoryUnselected(category: CategoryUi) {
        updateSelectedCategoriesAndLoadSummaries { it.remove(category) }
    }

    private fun updateSelectedCategoriesAndLoadSummaries(addOrRemoveAction: (MutableList<CategoryUi>) -> Unit) {
        val selectedCategories = _state.value.selectedCategories
        addOrRemoveAction(selectedCategories)
        _state.update {
            it.copy(
                progressState = ProgressState.Show,
                selectedCategories = selectedCategories
            )
        }
        val categoryIds = selectedCategories.map { it.id }
        loadSummariesForCategories(categoryIds)
    }

    /**
     * Load summaries for the given categories
     */
    private fun loadSummariesForCategories(categoryIds: List<String>) {
        viewModelScope.launch {
            summaryInteractor
                .getSummariesForCategories(categoryIds)
                .onSuccess { summaries ->
                    _state.update {
                        it.copy(
                            progressState = ProgressState.Hide,
                            summaries =
                            summaries.map { summary ->
                                summary.toSummaryUi()
                            }
                        )
                    }
                }.onFailure {
                    Timber.e(it, "SearchViewModel", "Error getting summaries by category")
                    _state.update { state ->
                        state.copy(error = it.toUiError())
                    }
                }
        }
    }

    fun onNavigateToSummary(summaryId: SummaryId) {
        postEvent(SearchUiEvent.NavigateTo.ToSummary(summaryId))
    }

    private fun postEvent(event: SearchUiEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
