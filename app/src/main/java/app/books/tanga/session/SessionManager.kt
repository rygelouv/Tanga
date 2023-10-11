package app.books.tanga.session

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    suspend fun openSession(sessionId: SessionId)

    suspend fun closeSession()

    suspend fun sessionState(): Flow<SessionState>
}
