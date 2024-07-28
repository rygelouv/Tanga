package app.books.tanga.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnboardingContainer(
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingScreen(
        onOnboardingComplete = onboardingViewModel::onOnboardingCompleted,
        onNavigateBack = onNavigateBack,
        onNavigateToAuth = onNavigateToAuth
    )
}
