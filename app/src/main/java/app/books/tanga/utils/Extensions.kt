package app.books.tanga.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Formats a long duration value (in milliseconds) into a time string in the format "MM:SS".
 *
 * @return The formatted time string.
 */
fun Long.toTimeFormat(): String {
    val totalSeconds = this / 1000
    val minutes = (totalSeconds / 60).toTwoDigitFormat()
    val remainingSeconds = (totalSeconds % 60).toTwoDigitFormat()
    return "$minutes:$remainingSeconds"
}

/**
 * Formats a long value to a two-digit string.
 *If the value is less than 10, it will be prefixed with a "0".
 *
 * @return The formatted string.
 */
fun Long.toTwoDigitFormat(): String = if (this < 10) "0$this" else this.toString()

/**
 * Converts the [Date] object into a formatted string.
 *
 * @param format The pattern describing the date and time format. Defaults to "yyyy-MM-dd HH:mm:ss".
 * @return A string representation of the date.
 */
fun Date.toFormattedString(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(this)
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Removes the currency symbol from a string.
 */
fun String.removeCurrencySymbol(): String = replace(Regex("[^\\d.]"), "").trim()

/**
 * Get the currency symbol from a string.
 */
fun String.extractCurrency(): String? = Regex("[^\\d.]").find(this)?.value?.trim()

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}

/**
 * extension function that safely strips the markdown-style json delimiters if present,
 * and returns only the JSON content itself. If no delimiters exist,
 * it returns the trimmed original string unchanged.
 */
fun String.stripJsonMarkdown(): String {
    val regex = Regex("""```json\s*(.*?)\s*```""", RegexOption.DOT_MATCHES_ALL)
    val matchResult = regex.find(this)
    return matchResult?.groupValues?.get(1)?.trim() ?: this.trim()
}
