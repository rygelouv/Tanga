package app.books.tanga.common.ui

/**
 * Represent a progress/loading operation on the UI
 */
sealed interface ProgressIndicatorState {

    /** When loading in progress */
    data object Show: ProgressIndicatorState

    /** When finished loading */
    data object Hide: ProgressIndicatorState
}