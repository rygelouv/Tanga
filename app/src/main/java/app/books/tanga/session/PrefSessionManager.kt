package app.books.tanga.session

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PrefSessionManager @Inject constructor(
    private val prefRepository: DefaultPrefDataStoreRepository,
    private val sessionDataCleaner: SessionDataCleaner,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SessionManager {
    override suspend fun openSession(sessionId: SessionId) {
        prefRepository.saveSessionId(sessionId)
    }

    override suspend fun closeSession() {
        sessionDataCleaner()
        prefRepository.removeSessionId()
    }

    override fun sessionState(): Flow<SessionState> =
        prefRepository.getSessionId()
            .map { sessionId ->
                when (sessionId) {
                    null -> SessionState.SignedOut
                    else -> SessionState.SignedIn(sessionId)
                }
            }.flowOn(ioDispatcher)
}
