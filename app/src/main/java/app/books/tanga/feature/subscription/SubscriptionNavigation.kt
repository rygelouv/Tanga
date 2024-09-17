package app.books.tanga.feature.subscription

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.subscription(onCloseClicked: () -> Unit, onNavigateToAuth: () -> Unit) {
    composable(route = NavigationScreen.Subscription.route) {
        SubscriptionContainer(
            onCloseClick = onCloseClicked,
            onNavigateToAuth = onNavigateToAuth
        )
    }
}

fun NavController.toSubscription(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.Subscription.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
