package app.books.tanga.feature.protectedaction

import app.books.tanga.entity.SummaryId

/**
 * Represents a protected action that requires authentication or subscription
 * or both to be performed.
 */
sealed class ProtectedAction {
    /**
     * Represents the action of reading a summary.
     * @param summaryId the id of the summary to read
     */
    data class Read(val summaryId: SummaryId) : ProtectedAction()

    /**
     * Represents the action of listening to a summary in audio format.
     * @param summaryId the id of the summary to listen to
     */
    data class Listen(val summaryId: SummaryId) : ProtectedAction()

    /**
     * Represents the action of saving a summary to the user's library.
     */
    data object Save : ProtectedAction()
}

/**
 * Represents the result of checking if a protected action is allowed to be performed.
 */
sealed class ProtectedActionCheckResult {
    /**
     * Represents the case where the user needs to authenticate to perform the action.
     */
    data object AuthRequired : ProtectedActionCheckResult()

    /**
     * Represents the case where the user needs to subscribe to perform the action.
     */
    data object SubscriptionRequired : ProtectedActionCheckResult()

    /**
     * Represents the case where the user is allowed to perform the action.
     */
    data object Allowed : ProtectedActionCheckResult()
}
