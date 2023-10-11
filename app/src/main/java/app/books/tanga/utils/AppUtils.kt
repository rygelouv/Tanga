package app.books.tanga.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import app.books.tanga.R

/**
 * Utility function to share a summary
 */
fun shareSummary(
    context: Context,
    summaryTitle: String,
    summaryAuthor: String,
    url: String = "https://ibb.co/bKn9yf6"
) {
    val sendIntent: Intent =
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                context.getString(
                    R.string.summary_details_share_message,
                    summaryTitle,
                    summaryAuthor,
                    url
                )
            )
            type = "text/plain"
        }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

/**
 * Utility function to open a link in a browser
 */
fun openLink(
    context: Context,
    url: String
) {
    val openLinkIntent: Intent =
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
    context.startActivity(openLinkIntent)
}
