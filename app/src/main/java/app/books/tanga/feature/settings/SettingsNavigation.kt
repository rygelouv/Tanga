package app.books.tanga.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.settings(
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToDeleteAccount: () -> Unit
) {
    composable(route = NavigationScreen.Settings.route) {
        SettingsContainer(
            onNavigateBack = onNavigateBack,
            onNavigateToAuth = onNavigateToAuth,
            onNavigateToDeleteAccount = onNavigateToDeleteAccount
        )
    }
}

fun NavController.toSettings(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.Settings.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
