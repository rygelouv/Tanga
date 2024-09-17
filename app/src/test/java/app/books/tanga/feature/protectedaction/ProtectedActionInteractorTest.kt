package app.books.tanga.feature.protectedaction

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.entity.SummaryId
import app.books.tanga.revenuecat.RevenueCatPurchases
import app.books.tanga.session.SessionManager
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProtectedActionInteractorTest {
    private val sessionManager = mockk<SessionManager>()
    private val revenuecatController = mockk<RevenueCatPurchases>()
    private val preferencesRepository = mockk<DefaultPrefDataStoreRepository>()
    private val interactor = ProtectedActionInteractor(sessionManager, revenuecatController, preferencesRepository)

    @BeforeEach
    fun setup() {
        coEvery { preferencesRepository.getWeeklySummary() } returns flowOf(SummaryId("weeklySummaryId"))
    }

    @Test
    fun `checkProtectedAction should return Allowed when user is not authenticated and tries to access weekly summary`() = runTest {
        coEvery { sessionManager.hasSession() } returns false
        val result = interactor.checkProtectedAction(
            ProtectedAction.SubscriptionRequiredAction.Listen(SummaryId("weeklySummaryId"))
        )
        assertEquals(ProtectedActionCheckResult.Allowed, result)
    }

    @Test
    fun `checkProtectedAction should return Allowed when user is authenticated and action is Save`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        val result = interactor.checkProtectedAction(ProtectedAction.AuthRequiredAction.Save)
        assertEquals(ProtectedActionCheckResult.Allowed, result)
    }

    @Test
    fun `checkProtectedAction should return Allowed when user is authenticated and action is Subscribe`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        val result = interactor.checkProtectedAction(ProtectedAction.AuthRequiredAction.Subscribe)
        assertEquals(ProtectedActionCheckResult.Allowed, result)
    }

    @Test
    fun `checkProtectedAction should return AuthRequired when user is not authenticated and action is Subscribe`() = runTest {
        coEvery { sessionManager.hasSession() } returns false
        val result = interactor.checkProtectedAction(ProtectedAction.AuthRequiredAction.Subscribe)
        assertEquals(ProtectedActionCheckResult.AuthRequired, result)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `checkProtectedAction should return Allowed when user is authenticated and action is Listen and user has active subscription`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        coEvery { revenuecatController.hasActiveSubscription() } returns true
        val result = interactor.checkProtectedAction(ProtectedAction.SubscriptionRequiredAction.Listen(SummaryId("1")))
        assertEquals(ProtectedActionCheckResult.Allowed, result)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `checkProtectedAction should return SubscriptionRequired when user is authenticated and action is Listen and user does not have active subscription`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        coEvery { revenuecatController.hasActiveSubscription() } returns false
        val result = interactor.checkProtectedAction(ProtectedAction.SubscriptionRequiredAction.Listen(SummaryId("1")))
        assertEquals(ProtectedActionCheckResult.SubscriptionRequired, result)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `checkProtectedAction should return Allowed when user is authenticated and action is Listen and action is upon weekly summary`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        coEvery { revenuecatController.hasActiveSubscription() } returns true
        val result = interactor.checkProtectedAction(
            ProtectedAction.SubscriptionRequiredAction.Listen(SummaryId("weeklySummaryId"))
        )
        assertEquals(ProtectedActionCheckResult.Allowed, result)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `checkProtectedAction should return Allowed when user is authenticated and action is Read and user has active subscription`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        coEvery { revenuecatController.hasActiveSubscription() } returns true
        val result = interactor.checkProtectedAction(ProtectedAction.SubscriptionRequiredAction.Read(SummaryId("1")))
        assertEquals(ProtectedActionCheckResult.Allowed, result)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `checkProtectedAction should return SubscriptionRequired when user is authenticated and action is Read and user does not have active subscription`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        coEvery { revenuecatController.hasActiveSubscription() } returns false
        val result = interactor.checkProtectedAction(ProtectedAction.SubscriptionRequiredAction.Read(SummaryId("1")))
        assertEquals(ProtectedActionCheckResult.SubscriptionRequired, result)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `checkProtectedAction should return Allowed when user is authenticated and action is Read and action is upon weekly summary`() = runTest {
        coEvery { sessionManager.hasSession() } returns true
        coEvery { revenuecatController.hasActiveSubscription() } returns true
        val result = interactor.checkProtectedAction(
            ProtectedAction.SubscriptionRequiredAction.Read(SummaryId("weeklySummaryId"))
        )
        assertEquals(ProtectedActionCheckResult.Allowed, result)
    }
}
