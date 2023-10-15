package app.books.tanga.session

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PrefSessionManagerTest {

    private val prefRepository: DefaultPrefDataStoreRepository = mockk(relaxed = true)
    private val sessionDataCleaner: SessionDataCleaner = mockk(relaxed = true)
    private val ioDispatcher = UnconfinedTestDispatcher()

    private lateinit var prefSessionManager: PrefSessionManager

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(ioDispatcher)
        prefSessionManager = PrefSessionManager(prefRepository, sessionDataCleaner, ioDispatcher)
    }

    @Test
    fun `openSession saves the session id`() = runTest {
        val sessionId = SessionId("test")

        prefSessionManager.openSession(sessionId)

        coVerify { prefRepository.saveSessionId(sessionId) }
    }

    @Test
    fun `closeSession cleans the session data and removes the session id`() = runTest {
        prefSessionManager.closeSession()

        coVerify { sessionDataCleaner() }
        coVerify { prefRepository.removeSessionId() }
    }

    @Test
    fun `sessionState returns SignedOut when session id is null`() = runTest {
        coEvery { prefRepository.getSessionId() } returns flowOf(null)

        val states = prefSessionManager.sessionState().toList()

        assert(states.size == 1 && states[0] == SessionState.SignedOut)
    }

    @Test
    fun `sessionState returns SignedIn with the correct session id when session id is not null`() =
        runTest {
            val sessionId = SessionId("test")
            coEvery { prefRepository.getSessionId() } returns flowOf(sessionId)

            val states = prefSessionManager.sessionState().toList()

            assert(states.size == 1 && states[0] == SessionState.SignedIn(sessionId))
        }
}
