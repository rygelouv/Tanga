package app.books.tanga.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AuthScreenContainer(
    onTermsAndPrivacyClick: () -> Unit,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val events by viewModel.events.collectAsStateWithLifecycle(AuthUiEvent.Empty)
    viewModel.onPageStarted()
    AuthScreen(
        state = state,
        events = events,
        modifier = modifier,
        onAuthSkip = { viewModel.onSkipAuth() },
        onAuthSuccess = onAuthSuccess,
        onGoogleSignInButtonClick = { viewModel.onGoogleSignInStarted() },
        onGoogleSignInComplete = { intent -> viewModel.onGoogleSignInCompleted(intent) },
        onGoogleSignInNotComplete = { viewModel.onGoogleSignInNotCompleted() },
        onTermsAndPrivacyClick = {
            viewModel.onTermsAndPrivacyClick()
            onTermsAndPrivacyClick()
        }
    )
}
