package app.books.tanga.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.di.DefaultDispatcher
import app.books.tanga.domain.summary.SummaryInteractor
import app.books.tanga.feature.summary.list.toSummaryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val summaryInteractor: SummaryInteractor,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState(progressState = ProgressState.Show))
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

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
        summaryInteractor.getCategories().onSuccess { categories ->
            _state.update {
                it.copy(
                    categories = categories.map { category ->
                        category.toCategoryUi()
                    })
            }
        }.onFailure {
            Log.e("SearchViewModel", "Error loading categories", it)
            // TODO: show error
        }
    }

    private suspend fun loadSummaries() {
        summaryInteractor.getAllSummaries().onSuccess { summaries ->
            _state.update {
                it.copy(
                    progressState = ProgressState.Hide,
                    summaries = summaries.map { summary ->
                        summary.toSummaryUi()
                    })
            }
        }.onFailure {
            Log.e("SearchViewModel", "Error loading summaries", it)
        }
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
            summaryInteractor.search(query).onSuccess { summaries ->
                _state.update {
                    it.copy(
                        progressState = ProgressState.Hide,
                        summaries = summaries.map { summary ->
                            summary.toSummaryUi()
                        })
                }
            }.onFailure {
                Log.e("SearchViewModel", "Error searching summaries", it)
            }
        }
    }

    /**
     * When a category is selected, we need add the category to the selected categories list
     * and load the summaries for the selected categories
     */
    fun onCategorySelected(category: CategoryUi) {
        val selectedCategories = _state.value.selectedCategories
        selectedCategories.add(category)
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
     * When a category is deselected, we need remove the category from the selected categories list
     * and load the summaries for the selected categories
     */
    fun onCategoryDeselected(category: CategoryUi) {
        val selectedCategories = _state.value.selectedCategories
        selectedCategories.remove(category)
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
            summaryInteractor.getSummariesForCategories(categoryIds).onSuccess { summaries ->
                _state.update {
                    it.copy(
                        progressState = ProgressState.Hide,
                        summaries = summaries.map { summary ->
                            summary.toSummaryUi()
                        })
                }
            }.onFailure {
                Log.e("SearchViewModel", "Error getting summaries by category", it)
            }
        }
    }

}