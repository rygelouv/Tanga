package app.books.tanga.feature.pricing

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.pricingPlan(onCloseClicked: () -> Unit = {}) {
    composable(route = NavigationScreen.PricingPlan.route) {
        PricingPlanScreen(onCloseClick = onCloseClicked)
    }
}

fun NavController.toPricingPlan(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.PricingPlan.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
