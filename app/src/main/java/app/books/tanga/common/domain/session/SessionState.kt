package app.books.tanga.common.domain.session

sealed class SessionState {
    data class LoggedIn(val sessionId: SessionId): SessionState()

    object LoggedOut: SessionState()
}

@JvmInline
value class SessionId(val value: String)