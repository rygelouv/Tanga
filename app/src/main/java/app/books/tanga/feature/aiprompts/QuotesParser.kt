package app.books.tanga.feature.aiprompts

import org.json.JSONArray

object QuotesParser {

    fun parseQuotes(jsonString: String): List<QuoteExplanation> {
        val jsonArray = JSONArray(jsonString)
        val quotes = mutableListOf<QuoteExplanation>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val quote = jsonObject.getString(QUOTE_KEY)
            val explanation = jsonObject.getString(EXPLANATION_KEY)

            quotes.add(QuoteExplanation(quote, explanation))
        }

        return quotes
    }
}

data class QuoteExplanation(
    val quote: String,
    val explanation: String
)

const val QUOTES_KEY = "quotes"
const val QUOTE_KEY = "quote"
const val EXPLANATION_KEY = "explanation"
