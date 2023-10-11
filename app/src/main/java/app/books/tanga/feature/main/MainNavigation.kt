package app.books.tanga.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

/**
 * Adds the main screen to the Nav Graph
 */
fun NavGraphBuilder.mainScreen(onLogout: () -> Unit) {
    composable(route = NavigationScreen.Main.route) {
        MainScreen(onLogout = onLogout)
    }
}

/**
 * Navigate to the main screen
 */
fun NavController.toMain(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.Main.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
