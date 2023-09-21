package app.books.tanga

import android.content.Context
import android.content.Intent
import app.books.tanga.feature.summary.SummaryUi

fun shareSummary(summary: SummaryUi, context: Context) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            context.getString(R.string.summary_details_share_message,
                summary.title, summary.author, "https://ibb.co/bKn9yf6")
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}