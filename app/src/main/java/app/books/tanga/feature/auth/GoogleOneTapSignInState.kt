package app.books.tanga.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class GoogleOneTapSignInState {

    var opened by mutableStateOf(false)
        private set

    fun open() {
        opened = true
    }

    internal fun close() {
        opened = false
    }
}