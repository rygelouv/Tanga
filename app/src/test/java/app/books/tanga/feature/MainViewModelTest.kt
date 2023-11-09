package app.books.tanga.feature

import app.books.tanga.feature.main.MainUiEvent
import app.books.tanga.feature.main.MainViewModel
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.books.tanga.session.SessionId
import app.books.tanga.session.SessionManager
import app.books.tanga.session.SessionState
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalTime
@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var sessionManager: SessionManager
    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setup() {
        // relaxed = true will allow returning default answers for non-mocked calls
        sessionManager = mockk(relaxed = true)
    }

    @Test
    fun `when session is SignedOut, emit NavigateTo Auth event`() = runTest {
        val sessionStateFlow = MutableSharedFlow<SessionState>()
        coEvery { sessionManager.sessionState() } returns sessionStateFlow.asSharedFlow()

        viewModel = MainViewModel(sessionManager)

        viewModel.event.test {
            sessionStateFlow.emit(SessionState.SignedOut)
            assert(awaitItem() is MainUiEvent.NavigateTo.ToAuth)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when session state is not SignedOut, do not emit NavigateTo Auth event`() = runTest {
        val sessionStateFlow = MutableSharedFlow<SessionState>()
        coEvery { sessionManager.sessionState() } returns sessionStateFlow.asSharedFlow()

        viewModel = MainViewModel(sessionManager)

        viewModel.event.test {
            sessionStateFlow.emit(SessionState.SignedIn(SessionId("SomeSessionID")))
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when no session state is emitted, do not emit any events`() = runTest {
        val sessionStateFlow = MutableSharedFlow<SessionState>()
        coEvery { sessionManager.sessionState() } returns sessionStateFlow.asSharedFlow()

        viewModel = MainViewModel(sessionManager)

        viewModel.event.test {
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when multiple session states are emitted, handle each state appropriately`() = runTest {
        val sessionStateFlow = MutableSharedFlow<SessionState>()
        coEvery { sessionManager.sessionState() } returns sessionStateFlow.asSharedFlow()

        viewModel = MainViewModel(sessionManager)

        viewModel.event.test {
            sessionStateFlow.emit(SessionState.SignedIn(SessionId("SomeSessionID")))
            expectNoEvents()
            sessionStateFlow.emit(SessionState.SignedOut)
            assert(awaitItem() is MainUiEvent.NavigateTo.ToAuth)
            sessionStateFlow.emit(SessionState.SignedIn(SessionId("SomeSessionID")))
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }
}
