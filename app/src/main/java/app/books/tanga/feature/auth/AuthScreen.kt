package app.books.tanga.feature.auth

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme

@Composable
fun AuthScreenContainer(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val events by viewModel.events.collectAsStateWithLifecycle(AuthUiEvent.Empty)
    AuthScreen(
        state = state,
        events = events,
        modifier = modifier,
        onAuthSkip = { viewModel.onSkipAuth() },
        onAuthSuccess = onAuthSuccess,
        onGoogleSignInButtonClick = { viewModel.onGoogleSignInStarted() },
        onGoogleSignInComplete = { intent -> viewModel.onGoogleSignInCompleted(intent) }
    )
}

@Composable
fun AuthScreen(
    state: AuthUiState,
    events: AuthUiEvent,
    onAuthSkip: () -> Unit,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    onGoogleSignInButtonClick: () -> Unit = {},
    onGoogleSignInComplete: (Intent) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier.padding(
                    top = 5.dp,
                    start = 5.dp,
                    end = LocalSpacing.current.extraLarge,
                    bottom = 5.dp
                ),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.weight(7f))
                Button(
                    modifier = Modifier
                        .width(120.dp)
                        .weight(3f),
                    colors =
                    ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                    onClick = {
                        onAuthSkip()
                    }
                ) {
                    when (state.skipProgressState) {
                        ProgressState.Hide -> {
                            Text(
                                text = stringResource(id = R.string.auth_skip),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        ProgressState.Show -> {
                            CircularProgressIndicator(
                                modifier = Modifier.width(24.dp).padding(top = 6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                trackColor = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }
        }
    ) {
        AuthContent(
            modifier = Modifier.padding(it),
            onAuthSuccess = onAuthSuccess,
            state = state,
            events = events,
            onGoogleSignInButtonClick = onGoogleSignInButtonClick,
            onGoogleSignInComplete = onGoogleSignInComplete
        )
    }
}

@Composable
fun AuthContent(
    state: AuthUiState,
    events: AuthUiEvent,
    onGoogleSignInButtonClick: () -> Unit,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    onGoogleSignInComplete: (Intent) -> Unit
) {
    SignIn(
        onAuthSuccess = onAuthSuccess,
        event = events,
        onGoogleSignInComplete = onGoogleSignInComplete
    )

    Column(
        modifier =
        modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier =
            Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = R.drawable.graphic_pricing),
            contentDescription = "app icon"
        )
        WelcomeMessageColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        // Google Sign In button
        GoogleSignInButton(state = state, onClick = onGoogleSignInButtonClick)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp)
                .padding(top = 14.dp),
            color = MaterialTheme.colorScheme.outline,
            text = stringResource(id = R.string.auth_terms_and_conditions),
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun WelcomeMessageColumn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.auth_welcome_to_tanga),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp)
                .padding(top = 10.dp)
                .weight(1f),
            color = MaterialTheme.colorScheme.outline,
            text = stringResource(id = R.string.auth_sign_up_with_google_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    val state = AuthUiState(
        googleSignInButtonProgressState = ProgressState.Hide
    )
    val events = AuthUiEvent.Empty
    TangaTheme {
        AuthScreen(onAuthSkip = {}, onAuthSuccess = {}, state = state, events = events)
    }
}
