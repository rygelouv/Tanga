package app.books.tanga.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.profile(
    onProClicked: () -> Unit = {},
    onRedirectToAuth: () -> Unit
) {
    composable(route = NavigationScreen.BottomBarScreen.Profile.route) {
        ProfileScreenContainer(onNavigateToPricing = onProClicked, onNavigateToAuth = onRedirectToAuth)
    }
}

fun NavController.toProfile(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.BottomBarScreen.Profile.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
