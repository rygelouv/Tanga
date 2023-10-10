package app.books.tanga.utils

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
