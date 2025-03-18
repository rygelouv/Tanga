package app.books.tanga.feature.aiprompts

import androidx.annotation.DrawableRes
import app.books.tanga.coreui.R
import app.books.tanga.coreui.resources.TextResource
import app.books.tanga.entity.AIPrompt
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.read.ReadSummaryUiState

data class AIPromptsUiState(
    val summaryId: SummaryId? = null,
    val loading: Boolean = false,
    val prompts: List<AIPromptUi>? = null,
    val selectedPrompt: AIPromptUi? = null,
    val promptResponseState: ReadSummaryUiState? = ReadSummaryUiState()
)

data class AIPromptUi(
    val id: String,
    @DrawableRes val icon: Int,
    val title: TextResource,
    val description: String
)

enum class PredefinedAIPrompts(val id: String, val icon: Int) {
    KEY_TAKEAWAYS("prompt_key_takeaways", R.drawable.key),
    APPLY_LESSONS_IN_LIFE("prompt_apply_lessons_in_life", R.drawable.book),
    QUOTES_FROM_BOOK("prompt_quotes_from_book", R.drawable.quote);

    companion object {
        fun fromId(id: String): PredefinedAIPrompts? = entries.find { it.id == id }
    }
}

fun AIPrompt.toUi(): AIPromptUi = AIPromptUi(
    id = id,
    icon = PredefinedAIPrompts.fromId(id)?.icon ?: R.drawable.idea,
    title = TextResource.fromText(title),
    description = description
)
