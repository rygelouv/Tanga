package app.books.tanga.common.domain.session

import kotlinx.coroutines.flow.StateFlow

interface SessionManager {

    suspend fun openSession(sessionId: SessionId)

    suspend fun closeSession()

    suspend fun sessionState(): StateFlow<SessionState>

}