package app.books.tanga.session

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.revenuecat.RevenueCatPurchases
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class ProtectedActionInteractor @Inject constructor(
    private val sessionManager: SessionManager,
    private val revenuecatController: RevenueCatPurchases,
    private val preferencesRepository: DefaultPrefDataStoreRepository
) {

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
