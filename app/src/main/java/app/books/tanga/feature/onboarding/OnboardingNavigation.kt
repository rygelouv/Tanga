package app.books.tanga.feature.onboarding

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen
import app.books.tanga.tracking.Pages

fun NavGraphBuilder.onboarding(
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    composable(route = NavigationScreen.Onboarding.route) {
        OnboardingContainer(
            onNavigateBack = onNavigateBack,
            onNavigateToAuth = onNavigateToAuth
        )
    }
}

fun NavController.toOnboarding(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.Onboarding.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}

fun NavGraphBuilder.landing(
    onNavigateToOnboarding: () -> Unit
) {
    composable(route = NavigationScreen.Landing.route) {
        val viewModel: OnboardingViewModel = hiltViewModel()
        viewModel.onPageStarted(Pages.LANDING)
        LandingScreen(
            onNavigateToOnboarding = {
                viewModel.onOnboardingStarted()
                onNavigateToOnboarding()
            }
        )
    }
}
