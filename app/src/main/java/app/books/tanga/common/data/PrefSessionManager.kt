package app.books.tanga.common.data

import app.books.tanga.common.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.common.di.IoDispatcher
import app.books.tanga.common.domain.session.SessionId
import app.books.tanga.common.domain.session.SessionManager
import app.books.tanga.common.domain.session.SessionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    override suspend fun sessionState(): StateFlow<SessionState> = withContext(ioDispatcher) {
        prefRepository.getSessionId()
            .map { sessionId ->
                when(sessionId) {
                    null -> SessionState.LoggedOut
                    else -> SessionState.LoggedIn(sessionId)
                }
            }.stateIn(this)
    }
}