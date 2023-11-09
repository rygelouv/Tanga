package app.books.tanga.feature

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.feature.splash.SplashViewModel
import app.books.tanga.navigation.NavigationScreen
import app.books.tanga.rule.MainCoroutineExtension
import app.books.tanga.session.SessionId
import app.books.tanga.session.SessionManager
import app.books.tanga.session.SessionState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
@ExperimentalCoroutinesApi
class SplashViewModelTest {

    private lateinit var repository: DefaultPrefDataStoreRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var viewModel: SplashViewModel

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        sessionManager = mockk(relaxed = true)
    }

    @Test
    fun `Onboarding not completed, navigate to onboarding screen`() = runTest {
        coEvery { repository.getOnboardingCompletionState() } returns false

        viewModel = SplashViewModel(repository, sessionManager)

        val state = viewModel.state
        assertEquals(NavigationScreen.Onboarding, state.value.startDestination)
    }

    @Test
    fun `Onboarding completed and signed out, navigate to authentication screen`() = runTest {
        coEvery { repository.getOnboardingCompletionState() } returns true
        coEvery { sessionManager.sessionState() } returns flowOf(SessionState.SignedOut)

        viewModel = SplashViewModel(repository, sessionManager)

        val state = viewModel.state
        assertEquals(NavigationScreen.Authentication, state.value.startDestination)
    }

    @Test
    fun `Onboarding completed and signed in, navigate to main screen`() = runTest {
        coEvery { repository.getOnboardingCompletionState() } returns true
        coEvery { sessionManager.sessionState() } returns flowOf(SessionState.SignedIn(SessionId("sessionId")))

        viewModel = SplashViewModel(repository, sessionManager)

        val state = viewModel.state
        assertEquals(NavigationScreen.Main, state.value.startDestination)
    }
}
