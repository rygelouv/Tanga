package app.books.tanga.session

import app.books.tanga.entity.UserId

sealed class SessionState {
    data class SignedIn(val sessionId: SessionId) : SessionState()

    data object SignedOut : SessionState()
}

@JvmInline
value class SessionId(
    val value: String
)

fun UserId.toSessionId(): SessionId = SessionId(value = value)
