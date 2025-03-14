package app.books.tanga.feature.aiprompts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.aiprompts.AIPromptsRepository
import app.books.tanga.entity.SummaryId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AIPromptsViewModel @Inject constructor(
    private val aiPromptsRepository: AIPromptsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<AIPromptsUiState> =
        MutableStateFlow(AIPromptsUiState())
    val state: StateFlow<AIPromptsUiState> = _state.asStateFlow()

    fun getAIPromptsForSummary(summaryId: SummaryId) {
        // val summaryId = SummaryId("designing_your_life")
        viewModelScope.launch {
            val result = aiPromptsRepository.getAIPromptsForSummary(summaryId)
            result.onSuccess {
                Timber.i("AIPrompts: $it")
                _state.update { state ->
                    state.copy(
                        prompts = it.map { prompt -> prompt.toUi() }
                    )
                }
            }
            result.onFailure {
                Timber.e(it, "Error getting AI prompts")
            }
        }
    }
}
