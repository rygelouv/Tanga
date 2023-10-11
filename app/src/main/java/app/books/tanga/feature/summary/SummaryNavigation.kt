package app.books.tanga.feature.summary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.feature.summary.details.SummaryDetailsScreen
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.summaryDetails(
    onBackClicked: () -> Unit,
    onPlayClicked: (String) -> Unit,
    onRecommendationClicked: (String) -> Unit
) {
    composable(
        route = NavigationScreen.SummaryDetails.route,
        arguments = listOf(
            navArgument(NavigationScreen.SummaryDetails.SUMMARY_ID_KEY) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        SummaryDetailsScreen(
            summaryId = backStackEntry
                .arguments
                ?.getString(NavigationScreen.SummaryDetails.SUMMARY_ID_KEY)!!,
            onBackClicked = onBackClicked,
            onPlayClicked = { summaryId -> onPlayClicked(summaryId) },
            onRecommendationClicked = onRecommendationClicked
        )
    }
}

fun NavController.toSummaryDetails(
    summaryId: String,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen
            .SummaryDetails
            .route
            .replace(
                oldValue = "{${NavigationScreen.SummaryDetails.SUMMARY_ID_KEY}}",
                newValue = summaryId
            )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
