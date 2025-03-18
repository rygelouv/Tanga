package app.books.tanga.feature.aiprompts

import com.google.firebase.Firebase
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.Schema
import com.google.firebase.vertexai.type.generationConfig
import com.google.firebase.vertexai.vertexAI

object CustomModelFactory {
    private val quotesJsonSchema = Schema.array(
        Schema.obj(
            mapOf(
                QUOTES_KEY to Schema.string(),
                EXPLANATION_KEY to Schema.string(),
            )
        )
    )

    private fun createModelForQuotesPrompt(): GenerativeModel = Firebase.vertexAI.generativeModel(
        modelName = GEMINI_QUOTES_PROMPT_MODEL,
        generationConfig = generationConfig {
            responseMimeType = "application/json"
            responseSchema = quotesJsonSchema
        }
    )

    fun createModel(promptId: String): GenerativeModel = when (promptId) {
        GEMINI_QUOTES_PROMPT_MODEL -> createModelForQuotesPrompt()
        else -> Firebase.vertexAI.generativeModel(GEMINI_QUOTES_PROMPT_MODEL)
    }

    fun createPromptForQuotesPrompt(booTitle: String): String = """
                Extract some of the most thought-provoking and impactful quotes from $booTitle. Include brief explanations.
                Present it only in json format "quotes and "explanation" keys. For example:
                [
                    {
                        "quote": "The only way to do great work is to love what you do.",
                        "explanation": "Steve Jobs believed that passion is the key to success."
                    },
                    {
                        "quote": "The best way to predict the future is to create it.",
                        "explanation": "Peter Drucker believed that we can shape our future."
                    }
               ]
               Don't wrap it in ```json``` or any other code block.
            """
}

const val GEMINI_QUOTES_PROMPT_MODEL = "gemini-2.0-flash"
