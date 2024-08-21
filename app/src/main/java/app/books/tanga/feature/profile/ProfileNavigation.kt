package app.books.tanga.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.profile(
    onProClicked: () -> Unit = {},
    onRedirectToAuth: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPrivacyAndTerms: () -> Unit
) {
    composable(route = NavigationScreen.BottomBarScreen.Profile.route) {
        ProfileScreenContainer(
            onNavigateToPricing = onProClicked,
            onNavigateToAuth = onRedirectToAuth,
            onNavigateToSettings = onNavigateToSettings,
            onNavigateToPrivacyAndTerms = onNavigateToPrivacyAndTerms
        )
    }
}

fun NavController.toProfile() {
    navigate(route = NavigationScreen.BottomBarScreen.Profile.route) {
        graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.privacyAndTerms(
    onNavigateBack: () -> Unit
) {
    composable(route = NavigationScreen.PrivacyAndTerms.route) {
        PrivacyAndTermsScreen(
            onNavigateBack = onNavigateBack
        )
    }
}

fun NavController.toPrivacyAndTerms(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.PrivacyAndTerms.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
