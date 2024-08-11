package app.books.tanga.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileScreenContainer(
    onNavigateToAuth: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPrivacyAndTerms: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToPricing: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(initialValue = ProfileUiEvent.Empty)

    HandleEvents(event = event, onNavigateToAuth = onNavigateToAuth, onNavigateToPricing = onNavigateToPricing)

    ProfileScreen(
        state = state,
        modifier = modifier,
        onProClick = { viewModel.onPremiumUpgrade() },
        onLoginClick = { viewModel.onLogin() },
        onSettingsClick = onNavigateToSettings,
        onPrivacyAndTermsClick = onNavigateToPrivacyAndTerms
    )
}
