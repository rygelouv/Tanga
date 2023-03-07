package app.books.tanga.common.di

import android.app.Application
import android.content.Context
import app.books.tanga.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

private const val WEB_CLIENT_ID = "webClientId"

class GoogleSignInModule {

    @Provides
    fun provideSignInClient(
        @ApplicationContext context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named(GOOGLE_SIGN_IN_REQUEST)
    fun provideSignInClient(@Named(WEB_CLIENT_ID) clientId: String): BeginSignInRequest {
        return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(true)
                .build())
            .setAutoSelectEnabled(true)
            .build()
    }

    @Provides
    @Named(Companion.GOOGLE_SIGN_UP_REQUEST)
    fun provideSignUpRequest(@Named(WEB_CLIENT_ID) clientId: String): BeginSignInRequest {
        return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(clientId)
                .setFilterByAuthorizedAccounts(true)
                .build()
        ).build()
    }

    @Provides
    @Named(WEB_CLIENT_ID)
    fun provideWebClientId(app: Application) = app.getString(R.string.web_client_id)

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST = "GoogleSignInRequest"
        const val GOOGLE_SIGN_UP_REQUEST = "GoogleSignUpRequest"

    }
}