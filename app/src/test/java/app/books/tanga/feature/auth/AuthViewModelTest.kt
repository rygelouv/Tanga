package app.books.tanga.feature.auth

import android.content.Intent
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.User
import app.books.tanga.errors.TangaErrorTracker
import app.books.tanga.errors.toUiError
import app.books.tanga.notifications.NotificationPermissionHandler
import app.books.tanga.notifications.NotificationPermissionTrigger
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.books.tanga.tracking.AnalyticsTracker
import app.cash.turbine.test
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineDispatcherExtension::class)
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel

    private val interactor: AuthenticationInteractor = mockk()
    private val signInClient: SignInClient = mockk()
    private val errorTracker: TangaErrorTracker = mockk()
    private val analyticsTracker: AnalyticsTracker = mockk(relaxUnitFun = true)
    private val notificationPermissionHandler: NotificationPermissionHandler = mockk()

    @Test
    fun `onGoogleSignInStarted - success scenario`() = runTest {
        val signInResult = mockk<BeginSignInResult>()
        coEvery { interactor.initGoogleSignIn() } returns Result.success(signInResult)
        coEvery {
            notificationPermissionHandler.shouldRequestNotificationPermission(NotificationPermissionTrigger.SKIP_AUTH)
        } returns false

        setupViewModel()

        viewModel.onGoogleSignInStarted()

        viewModel.state.test {
            val state = awaitItem()
            Assertions.assertEquals(ProgressState.Show, state.googleSignInButtonProgressState)
        }

        viewModel.events.test {
            val event = expectMostRecentItem()

            Assertions.assertTrue(event is AuthUiEvent.LaunchGoogleSignIn)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onGoogleSignInStarted - failure scenario`() = runTest {
        val error = RuntimeException("Error")
        coEvery { interactor.initGoogleSignIn() } returns Result.failure(error)

        setupViewModel()

        viewModel.onGoogleSignInStarted()

        viewModel.state.test {
            val state = awaitItem()
            Assertions.assertEquals(ProgressState.Hide, state.googleSignInButtonProgressState)
        }

        viewModel.events.test {
            val event = expectMostRecentItem()
            Assertions.assertEquals(AuthUiEvent.Error(error.toUiError()), event)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSkipAuth - success scenario`() = runTest {
        val user = mockk<User>(relaxed = true)
        coEvery { interactor.signInAnonymously() } returns Result.success(user)
        coEvery { interactor.isUserAnonymous() } returns false
        coEvery { errorTracker.setUserDetails(any(), any()) } returns Unit
        coEvery {
            notificationPermissionHandler.shouldRequestNotificationPermission(NotificationPermissionTrigger.SKIP_AUTH)
        } returns false

        setupViewModel()

        viewModel.onSkipAuth()

        viewModel.state.test {
            Assertions.assertEquals(ProgressState.Show, awaitItem().skipProgressState)
        }

        viewModel.events.test {
            Assertions.assertEquals(AuthUiEvent.NavigateTo.ToHomeScreen, expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSkipAuth - success scenario with notification permission to be requested`() = runTest {
        val user = mockk<User>(relaxed = true)
        coEvery { interactor.signInAnonymously() } returns Result.success(user)
        coEvery { interactor.isUserAnonymous() } returns false
        coEvery { errorTracker.setUserDetails(any(), any()) } returns Unit
        coEvery {
            notificationPermissionHandler.shouldRequestNotificationPermission(NotificationPermissionTrigger.SKIP_AUTH)
        } returns true

        setupViewModel()

        viewModel.onSkipAuth()

        viewModel.state.test {
            Assertions.assertEquals(ProgressState.Show, awaitItem().skipProgressState)
        }

        viewModel.events.test {
            Assertions.assertEquals(AuthUiEvent.NavigateTo.ToNotificationPermissionScreen, expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSkipAuth - close scenario`() = runTest {
        coEvery { interactor.isUserAnonymous() } returns true
        coEvery { errorTracker.setUserDetails(any(), any()) } returns Unit

        setupViewModel()

        viewModel.onSkipAuth()

        viewModel.state.test {
            Assertions.assertEquals(ProgressState.Hide, awaitItem().skipProgressState)
        }

        viewModel.events.test {
            Assertions.assertEquals(AuthUiEvent.Close, expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSkipAuth - failure scenario`() = runTest {
        val error = RuntimeException("Error")
        coEvery { interactor.signInAnonymously() } returns Result.failure(error)
        coEvery { interactor.isUserAnonymous() } returns false

        setupViewModel()

        viewModel.onSkipAuth()

        viewModel.state.test {
            val state = awaitItem()
            Assertions.assertEquals(ProgressState.Hide, state.skipProgressState)
        }

        viewModel.events.test {
            Assertions.assertEquals(AuthUiEvent.Error(error.toUiError()), expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onGoogleSignInCompleted - success scenario`() = runTest {
        val intent = mockk<Intent>()
        val credentials = mockk<SignInCredential>()
        val user = mockk<User>(relaxed = true)
        coEvery { signInClient.getSignInCredentialFromIntent(intent) } returns credentials
        coEvery { interactor.completeGoogleSignIn(credentials) } returns Result.success(user)
        coEvery { errorTracker.setUserDetails(any(), any()) } returns Unit

        setupViewModel()

        viewModel.onGoogleSignInCompleted(intent)

        // viewModel.state.first().googleSignInButtonProgressState shouldBe ProgressState.Hide
        viewModel.state.test {
            Assertions.assertEquals(ProgressState.Hide, awaitItem().googleSignInButtonProgressState)
        }

        viewModel.events.test {
            Assertions.assertEquals(AuthUiEvent.NavigateTo.ToHomeScreen, expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onGoogleSignInCompleted - failure scenario`() = runTest {
        val intent = mockk<Intent>()
        val credentials = mockk<SignInCredential>()
        val error = RuntimeException("Error")
        coEvery { signInClient.getSignInCredentialFromIntent(intent) } returns credentials
        coEvery { interactor.completeGoogleSignIn(credentials) } returns Result.failure(error)

        setupViewModel()

        viewModel.onGoogleSignInCompleted(intent)

        viewModel.state.test {
            val state = awaitItem()
            Assertions.assertEquals(ProgressState.Hide, state.skipProgressState)
        }

        viewModel.events.test {
            Assertions.assertEquals(AuthUiEvent.Error(error.toUiError()), expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun setupViewModel() {
        viewModel = AuthViewModel(
            interactor = interactor,
            signInClient = signInClient,
            errorTracker = errorTracker,
            analyticsTracker = analyticsTracker,
            notificationPermissionHandler = notificationPermissionHandler
        )
    }
}
