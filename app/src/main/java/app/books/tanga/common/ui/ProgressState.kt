package app.books.tanga.common.ui

/**
 * Represent a progress/loading operation on the UI
 */
sealed interface ProgressState {

    /** When loading in progress */
    data object Show: ProgressState

    /** When finished loading */
    data object Hide: ProgressState
}