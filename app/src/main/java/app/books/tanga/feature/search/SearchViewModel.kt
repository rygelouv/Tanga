package app.books.tanga.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressIndicatorState
import app.books.tanga.data.CategoryRepository
import app.books.tanga.data.SummaryRepository
import app.books.tanga.domain.summary.SummaryInteractor
import app.books.tanga.feature.summary.list.toSummaryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val summaryInteractor: SummaryInteractor
) : ViewModel() {

    private val _state: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState(progressIndicatorState = ProgressIndicatorState.Show))
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    init {
        loadSearchData()
    }

    private fun loadSearchData() {
        viewModelScope.launch {
            loadCategories()

            loadSummaries()
        }
    }

    private suspend fun loadCategories() {
        summaryInteractor.getCategories().onSuccess { categories ->
            _state.update {
                it.copy(
                    progressIndicatorState = ProgressIndicatorState.Hide,
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
                    progressIndicatorState = ProgressIndicatorState.Hide,
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
        _state.update { it.copy(query = query, shouldShowCategories = query.isEmpty()) }

        viewModelScope.launch {
            summaryInteractor.search(query).onSuccess { summaries ->
                _state.update {
                    it.copy(summaries = summaries.map { summary ->
                        summary.toSummaryUi()
                    })
                }
            }.onFailure {
                Log.e("SearchViewModel", "Error searching summaries", it)
            }
        }
    }

    fun onCategorySelected(category: CategoryUi) {
        _state.update { it.copy(selectedCategory = category) }

        viewModelScope.launch {
            summaryInteractor.getSummariesByCategory(category.slug).onSuccess { summaries ->
                _state.update {
                    it.copy(summaries = summaries.map { summary ->
                        summary.toSummaryUi()
                    })
                }
            }.onFailure {
                Log.e("SearchViewModel", "Error getting summaries by category", it)
            }
        }
    }

}