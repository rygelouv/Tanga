package app.books.tanga.feature.aiprompts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.data.aiprompts.AIPromptsRepository
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.read.ContentType
import app.books.tanga.feature.read.ReadSummaryUiState
import app.books.tanga.feature.read.toScaleFactor
import app.books.tanga.utils.stripJsonMarkdown
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
        val summaryId = SummaryId("designing_your_life")
        viewModelScope.launch {
            val result = aiPromptsRepository.getAIPromptsForSummary(summaryId)
            result.onSuccess {
                Timber.i("AIPrompts: $it")
                _state.update { state ->
                    state.copy(
                        summaryId = summaryId,
                        prompts = it.map { prompt -> prompt.toUi() }
                    )
                }
            }
            result.onFailure {
                Timber.e(it, "Error getting AI prompts")
            }
        }
    }

    fun onPromptSelected(promptId: String) {
        _state.update { state ->
            state.copy(
                selectedPrompt = state.prompts?.find { it.id == promptId }
            )
        }
    }

    fun onPromptResponseBackClicked() {
        _state.update { state ->
            state.copy(
                selectedPrompt = null,
                promptResponseState = ReadSummaryUiState()
            )
        }
    }

    fun getAIPromptResponse() {
        val prompt = _state.value.selectedPrompt
        viewModelScope.launch {
            prompt?.let {
                val generativeModel = CustomModelFactory.createModel(it.id)
                val promptDescription = if (it.id == PredefinedAIPrompts.QUOTES_FROM_BOOK.id) {
                    CustomModelFactory.createPromptForQuotesPrompt(_state.value.summaryId?.value.orEmpty())
                } else {
                    it.description
                }
                val response = generativeModel.generateContent(promptDescription)
                Timber.e(response.text)
                response.text?.let { responseText ->
                    _state.update { state ->
                        state.copy(
                            promptResponseState = ReadSummaryUiState(
                                progressState = ProgressState.Hide,
                                summaryTextContent = responseText,
                                contentType = getContent(it.id, responseText)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getContent(promptId: String, responseText: String): ContentType = when (promptId) {
        PredefinedAIPrompts.QUOTES_FROM_BOOK.id -> {
            val quotes = QuotesParser.parseQuotes(responseText.stripJsonMarkdown())
            ContentType.Quotes(quotes)
        }
        else -> ContentType.Markdown(responseText)
    }

    fun onFontSizeClicked() {
        _state.update { currentState ->
            val promptResponseState = currentState.promptResponseState
            currentState.copy(
                promptResponseState = promptResponseState?.copy(
                    fontSizeChooserVisible = !promptResponseState.fontSizeChooserVisible
                )
            )
        }
    }

    fun onScaleChanged(scale: Float) {
        val newTextScaleFactor = scale.toScaleFactor()
        _state.update { currentState ->
            currentState.copy(
                promptResponseState = currentState.promptResponseState?.copy(
                    textScaleFactor = newTextScaleFactor
                )
            )
        }
    }
}
