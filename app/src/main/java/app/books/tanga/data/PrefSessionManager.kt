package app.books.tanga.data

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.di.IoDispatcher
import app.books.tanga.domain.session.SessionId
import app.books.tanga.domain.session.SessionManager
import app.books.tanga.domain.session.SessionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PrefSessionManager @Inject constructor(
    private val prefRepository: DefaultPrefDataStoreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SessionManager {

    override suspend fun openSession(sessionId: SessionId) {
        prefRepository.saveSessionId(sessionId)
    }

    override suspend fun closeSession() {
        prefRepository.removeSessionId()
    }

    override suspend fun sessionState(): Flow<SessionState> = withContext(ioDispatcher) {
        prefRepository.getSessionId()
            .map { sessionId ->
                when(sessionId) {
                    null -> SessionState.LoggedOut
                    else -> SessionState.LoggedIn(sessionId)
                }
            }
    }
}