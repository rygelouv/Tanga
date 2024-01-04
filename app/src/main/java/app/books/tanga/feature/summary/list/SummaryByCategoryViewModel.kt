package app.books.tanga.feature.summary.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.CategoryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.search.getCategoryIllustration
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
import kotlinx.coroutines.launch

@HiltViewModel
class SummaryByCategoryViewModel @Inject constructor(
    private val summaryInteractor: SummaryInteractor
) : ViewModel() {

    private val _state: MutableStateFlow<SummariesByCategoryUiState> =
        MutableStateFlow(SummariesByCategoryUiState(progressState = ProgressState.Show))
    val state: StateFlow<SummariesByCategoryUiState> = _state.asStateFlow()

    private val _events: Channel<SummariesByCategoryUiEvent> = Channel()
    val events: Flow<SummariesByCategoryUiEvent> = _events.receiveAsFlow()

    fun loadSummaries(categoryId: CategoryId, categoryName: String) {
        updateCategoryInfo(categoryId, categoryName)

        viewModelScope.launch {
            summaryInteractor
                .getSummariesByCategory(categoryId.value)
                .onSuccess { summaries ->
                    _state.value = _state.value.copy(
                        progressState = ProgressState.Hide,
                        summaries = summaries.map { summary ->
                            summary.toSummaryUi()
                        }
                    )
                }.onFailure { error ->
                    _state.value = _state.value.copy(
                        progressState = ProgressState.Hide,
                        error = error.toUiError()
                    )
                }
        }
    }

    private fun updateCategoryInfo(categoryId: CategoryId, categoryName: String) {
        val category = CategoryUi(
            id = categoryId.value,
            name = categoryName,
            icon = getCategoryIllustration(categoryId)
        )
        _state.value = _state.value.copy(
            progressState = ProgressState.Show,
            categoryUi = category
        )
    }
}
