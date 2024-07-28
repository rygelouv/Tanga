package app.books.tanga.feature.deleteaccount

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.deleteAccount(
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    composable(route = NavigationScreen.DeleteAccount.route) {
        DeleteAccountContainer(
            onNavigateBack = onNavigateBack,
            onNavigateToAuth = onNavigateToAuth
        )
    }
}

fun NavController.toDeleteAccount(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.DeleteAccount.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
