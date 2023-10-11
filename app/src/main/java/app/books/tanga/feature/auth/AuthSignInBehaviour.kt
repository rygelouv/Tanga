package app.books.tanga.feature.auth

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState

@Composable
fun SignIn(
    onAuthSuccess: () -> Unit,
    event: AuthUiEvent,
    onGoogleSignInCompleted: (Intent) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let(onGoogleSignInCompleted)
            }
        }

    when (event) {
        is AuthUiEvent.LaunchGoogleSignIn -> {
            LaunchedEffect(event) {
                val intentSender = event.signInResult.pendingIntent.intentSender
                val intent = IntentSenderRequest.Builder(intentSender).build()
                launcher.launch(intent)
            }
        }
        is AuthUiEvent.NavigateTo.ToHomeScreen -> {
            LaunchedEffect(Unit) {
                onAuthSuccess()
            }
        }
        else -> Unit
    }
}

@Composable
fun GoogleSignInButton(
    state: AuthUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val progressState = state.googleSignInButtonProgressState
    Button(
        modifier = modifier
            .height(55.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Transparent,
            containerColor = Color.Black
        ),
        shape = RoundedCornerShape(6.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
        enabled = progressState != ProgressState.Show
    ) {
        Box {
            when (progressState) {
                ProgressState.Show ->
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp),
                        color = Color.White
                    )
                else -> {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.google_logo),
                        contentDescription = "google logo",
                        modifier = Modifier.size(22.dp)
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.auth_sign_in_with_google),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}
