package app.books.tanga

/**
 * Formats a long duration value (in milliseconds) into a time string in the format "MM:SS".
 *
 * @return The formatted time string.
 */
fun Long.toTimeFormat(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return "${minutes}:${remainingSeconds}"
}