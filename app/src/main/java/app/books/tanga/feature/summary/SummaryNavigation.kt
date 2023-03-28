package app.books.tanga.feature.summary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.feature.summary.details.SummaryDetailsScreen
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.summaryDetails() {
    composable(route = NavigationScreen.SummaryDetails.route) {
        SummaryDetailsScreen()
    }
}

fun NavController.toSummaryDetails(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.SummaryDetails.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}