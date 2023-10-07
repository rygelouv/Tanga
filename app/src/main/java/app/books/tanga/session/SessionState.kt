package app.books.tanga.session

sealed class SessionState {
    data class SignedIn(val sessionId: SessionId): SessionState()

    data object SignedOut: SessionState()
}

@JvmInline
value class SessionId(val value: String)