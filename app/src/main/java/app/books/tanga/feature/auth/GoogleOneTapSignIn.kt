package app.books.tanga.feature.auth

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import kotlinx.coroutines.tasks.await

@Composable
fun rememberGoogleOneTapSignInState(): GoogleOneTapSignInState {
    return remember { GoogleOneTapSignInState() }
}

@Composable
fun GoogleOneTapSignIn(
    state: GoogleOneTapSignInState,
    clientId: String,
    nonce: String,
    onTokenIdReceived: (String) -> Unit,
    onOneTapDialogDismissed: (String) -> Unit
) {
    val activity = LocalContext.current as Activity
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        runCatching {
            when (activityResult.resultCode) {
                Activity.RESULT_OK -> {
                    val oneTapClient = Identity.getSignInClient(activity)
                    val credentials =
                        oneTapClient.getSignInCredentialFromIntent(activityResult.data)
                    credentials.googleIdToken?.let {
                        onTokenIdReceived(it)
                        state.close()
                    }
                }
                else -> {
                    onOneTapDialogDismissed("Dialog Closed")
                    state.close()
                }
            }
        }.onFailure {
            val error = (it as? ApiException) ?: return@onFailure
            when (error.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    onOneTapDialogDismissed("Dialog Canceled")
                    state.close()
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    onOneTapDialogDismissed("Network Error")
                    state.close()
                }
                else -> {
                    onOneTapDialogDismissed(error.message.toString())
                    state.close()
                }
            }
        }
    }

    LaunchedEffect(key1 = state.opened) {
        if (state.opened) {
            signIn(
                activity = activity,
                clientId = clientId,
                nonce = nonce,
                launchActivityResult = { intentSenderRequest ->
                    activityLauncher.launch(intentSenderRequest)
                },
                onError = {
                    onOneTapDialogDismissed(it)
                    state.close()
                }
            )
        }
    }
}

private suspend fun signIn(
    activity: Activity,
    clientId: String,
    nonce: String,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit
) {
    val oneTapClient = Identity.getSignInClient(activity)
    val signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            .setNonce(nonce)
            .setServerClientId(clientId)
            .setFilterByAuthorizedAccounts(true)
            .build()
    ).setAutoSelectEnabled(true).build()

    val result = runCatching {
        oneTapClient.beginSignIn(signInRequest).await()
    }.onFailure {
        signUp(
            activity = activity,
            clientId = clientId,
            nonce = nonce,
            launchActivityResult = launchActivityResult,
            onError = onError
        )
    }.getOrNull()

    result?.let {
        launchActivityResult(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
    } ?: onError("Unable to get sign in result")
}

private suspend fun signUp(
    activity: Activity,
    clientId: String,
    nonce: String,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit
) {
    val oneTapClient = Identity.getSignInClient(activity)
    val signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            .setNonce(nonce)
            .setServerClientId(clientId)
            .setFilterByAuthorizedAccounts(true)
            .build()
    ).build()

    val result = runCatching {
        oneTapClient.beginSignIn(signInRequest).await()
    }.onFailure {
       onError("Google Account not found")
        Log.d("MainActivity", "exception ==> $it")
    }.getOrNull()

    result?.let {
        launchActivityResult(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
    } ?: onError("Unable to get sign up result")
}