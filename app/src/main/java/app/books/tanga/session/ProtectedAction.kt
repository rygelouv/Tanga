package app.books.tanga.session

import app.books.tanga.entity.SummaryId

sealed class ProtectedAction {
    data class Read(val summaryId: SummaryId) : ProtectedAction()
    data class Listen(val summaryId: SummaryId) : ProtectedAction()
    data object Save : ProtectedAction()
}

sealed class ProtectedActionCheckResult {
    data object AuthRequired : ProtectedActionCheckResult()
    data object SubscriptionRequired : ProtectedActionCheckResult()
    data object Allowed : ProtectedActionCheckResult()
}
