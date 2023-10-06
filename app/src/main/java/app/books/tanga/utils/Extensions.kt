package app.books.tanga.utils

/**
 * Formats a long duration value (in milliseconds) into a time string in the format "MM:SS".
 *
 * @return The formatted time string.
 */
fun Long.toTimeFormat(): String {
    val totalSeconds = this / 1000
    val minutes = (totalSeconds / 60).toTwoDigitFormat()
    val remainingSeconds = (totalSeconds % 60).toTwoDigitFormat()
    return "${minutes}:${remainingSeconds}"
}

/**
 * Formats a long value to a two-digit string.
 *If the value is less than 10, it will be prefixed with a "0".
 *
 * @return The formatted string.
 */
fun Long.toTwoDigitFormat(): String {
    return if (this < 10) "0${this}" else this.toString()
}