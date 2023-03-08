package app.books.tanga.common.ui

/**
 * Represent a progress/loading operation on the UI
 */
sealed interface ProgressState {

    /** When loading in progress */
    object Loading: ProgressState

    /** When finished loading */
    object Finished: ProgressState
}