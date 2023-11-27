package app.books.tanga.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

/**
 * Adds the Authentication screen to the Nav Graph
 */
fun NavGraphBuilder.authentication(
    onAuthSuccess: () -> Unit
) {
    composable(route = NavigationScreen.Authentication.route) {
        AuthScreenContainer(
            onAuthSuccess = onAuthSuccess
        )
    }
}

/**
 * Navigate to the authentication screen
 */
fun NavController.toAuthentication(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.Authentication.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
