package app.books.tanga.feature

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.TangaErrorTracker
import app.books.tanga.feature.auth.AuthUiEvent
import app.books.tanga.feature.auth.AuthViewModel
import app.books.tanga.feature.auth.AuthenticationInteractor
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

    @Test
    fun `onGoogleSignInStarted - success scenario`() = runTest {
        val signInResult = mockk<BeginSignInResult>()
        coEvery { interactor.initGoogleSignIn() } returns Result.success(signInResult)

        viewModel = AuthViewModel(
            interactor = interactor,
            signInClient = signInClient,
            errorTracker = errorTracker
        )

        viewModel.onGoogleSignInStarted()

        viewModel.state.test {
            val state = awaitItem()
            Assertions.assertEquals(ProgressState.Show, state.googleSignInButtonProgressState)
        }

        val event = viewModel.events.first()
        Assertions.assertTrue(event is AuthUiEvent.LaunchGoogleSignIn)

        /*viewModel.events.test {
            val event = expectMostRecentItem()

            Assertions.assertTrue(event is AuthUiEvent.LaunchGoogleSignIn)
            cancelAndConsumeRemainingEvents()
        }*/
    }
}
