package app.books.tanga.feature.auth

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.main.toMain
import app.books.tanga.navigation.NavigationScreen

@Composable
fun SignIn(
    onAuthSuccess: () -> Unit,
    event: AuthUiEvent,
    onGoogleSignInCompleted: (Intent) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
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
    onClick: () -> Unit,
    state: AuthUiState
) {
    val progressState = state.googleSignInButtonProgressState
    Button(
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Transparent,
            backgroundColor = Color.Black,
        ),
        shape = RoundedCornerShape(6.dp),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        enabled = progressState != ProgressState.Loading
    ) {
        Box {
            when (progressState) {
                ProgressState.Loading -> CircularProgressIndicator(
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
                        text = "Sign in with Google",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}
