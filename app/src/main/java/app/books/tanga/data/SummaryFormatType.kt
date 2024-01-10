package app.books.tanga.data

/**
 * Enum class representing different types of summary resource formats
 * and their associated filenames.
 */
enum class SummaryFormatType(val filename: String) {
    COVER("cover.jpg"),
    TEXT("summary.md"),
    AUDIO("audio.mp3"),
    GRAPHIC("graphic.jpg"),
}
