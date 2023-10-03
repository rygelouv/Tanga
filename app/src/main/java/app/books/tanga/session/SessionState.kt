package app.books.tanga.session

sealed class SessionState {
    data class LoggedIn(val sessionId: SessionId): SessionState()

    data object LoggedOut: SessionState()
}

@JvmInline
value class SessionId(val value: String)