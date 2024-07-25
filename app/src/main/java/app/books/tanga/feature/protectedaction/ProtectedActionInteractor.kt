package app.books.tanga.feature.protectedaction

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.revenuecat.RevenueCatPurchases
import app.books.tanga.session.SessionManager
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class ProtectedActionInteractor @Inject constructor(
    private val sessionManager: SessionManager,
    private val revenuecatController: RevenueCatPurchases,
    private val preferencesRepository: DefaultPrefDataStoreRepository
) {

    /**
     * Check if the user is allowed to perform the given action
     * - If the user is not authenticated, [ProtectedActionCheckResult.AuthRequired] is returned
     * regardless of the action
     * - If the user is authenticated and the action is a read or listen action, the user needs to
     * have an active subscription
     *  to perform the action, otherwise [ProtectedActionCheckResult.SubscriptionRequired] is returned
     *  - If the user is authenticated and the action is a save action, [ProtectedActionCheckResult.Allowed] is returned
     * @param action the [ProtectedAction] to check
     */
    suspend fun checkProtectedAction(action: ProtectedAction): ProtectedActionCheckResult {
        if (sessionManager.hasSession().not()) {
            return ProtectedActionCheckResult.AuthRequired
        }

        return when (action) {
            is ProtectedAction.Listen, is ProtectedAction.Read -> handleListenOrReadAction(action)
            is ProtectedAction.Save -> ProtectedActionCheckResult.Allowed
        }
    }

    private suspend fun handleListenOrReadAction(action: ProtectedAction): ProtectedActionCheckResult = when {
        action.isUponWeeklySummary() -> ProtectedActionCheckResult.Allowed
        revenuecatController.hasActiveSubscription().not() -> ProtectedActionCheckResult.SubscriptionRequired
        else -> ProtectedActionCheckResult.Allowed
    }

    private suspend fun ProtectedAction.isUponWeeklySummary(): Boolean {
        val weeklySummary = preferencesRepository.getWeeklySummary().first() ?: return false
        return when (this) {
            is ProtectedAction.Listen -> summaryId == weeklySummary
            is ProtectedAction.Read -> summaryId == weeklySummary
            else -> false
        }
    }
}
