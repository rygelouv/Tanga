package app.books.tanga.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import app.books.tanga.tracking.Pages

@Composable
fun OnboardingContainer(
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    onboardingViewModel.onPageStarted(Pages.ONBOARDING)
    OnboardingScreen(
        onOnboardingComplete = onboardingViewModel::onOnboardingCompleted,
        onNavigateBack = onNavigateBack,
        onNavigateToAuth = onNavigateToAuth
    )
}
