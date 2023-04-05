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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun AuthScreen(
    onAuthSkipped: () -> Unit,
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.weight(8f))
                Button(
                    modifier = Modifier
                        .size(76.dp)
                        .weight(2f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                    shape = RoundedCornerShape(40.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                    onClick = {
                        onAuthSkipped()
                    }
                ) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val events by viewModel.events.collectAsStateWithLifecycle(AuthUiEvent.Empty)
        AuthContent(
            modifier = Modifier.padding(it),
            onAuthSuccess = onAuthSuccess,
            state = state,
            event = events,
            onGoogleSignInButtonClick = { viewModel.onGoogleSignInStarted() },
            onGoogleSignInCompleted = { intent -> viewModel.onGoogleSignInCompleted(intent) }
        )
    }
}

@Composable
fun AuthContent(
    modifier: Modifier,
    onAuthSuccess: () -> Unit,
    state: AuthUiState,
    event: AuthUiEvent,
    onGoogleSignInButtonClick: () -> Unit,
    onGoogleSignInCompleted: (Intent) -> Unit
) {
    SignIn(
        onAuthSuccess = onAuthSuccess,
        event = event,
        onGoogleSignInCompleted = onGoogleSignInCompleted
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = R.drawable.graphic_pricing),
            contentDescription = "app icon"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Welcome to Tanga",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 28.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 41.dp)
                    .padding(top = 10.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.outline,
                text = "Sign in or Sign up with Google to start enjoying Tanga Now",
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        // Google Sign In button
        GoogleSignInButton(onGoogleSignInButtonClick, state)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp)
                .padding(top = 14.dp),
            color = MaterialTheme.colorScheme.outline,
            text = "Terms and Conditions",
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen({}, {})
}