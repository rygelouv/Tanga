package app.books.tanga.session

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface SessionManager {
    suspend fun openSession(sessionId: SessionId)

    suspend fun closeSession()

    fun sessionState(): Flow<SessionState>

    suspend fun hasSession(): Boolean = sessionState().first() is SessionState.SignedIn
}
