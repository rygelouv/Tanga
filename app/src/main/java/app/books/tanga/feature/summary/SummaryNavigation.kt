package app.books.tanga.feature.summary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.details.SummaryDetailsScreenContainer
import app.books.tanga.navigation.NavigationScreen

@Suppress("LongParameterList")
fun NavGraphBuilder.summaryDetails(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToSubscriptions: () -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToReadSummaryScreen: (SummaryId) -> Unit,
    onNavigateToPermissionExplainer: () -> Unit,
    onNavigateToRecommendedSummaryDetails: (SummaryId) -> Unit,
    onNavigateToAIPrompts: (SummaryId) -> Unit
) {
    composable(
        route = NavigationScreen.SummaryDetails.route,
        arguments = listOf(
            navArgument(NavigationScreen.SummaryDetails.SUMMARY_ID_KEY) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val summaryId = SummaryId(
            value = backStackEntry
                .arguments
                ?.getString(NavigationScreen.SummaryDetails.SUMMARY_ID_KEY)!!
        )
        SummaryDetailsScreenContainer(
            summaryId = summaryId,
            onNavigateToAuth = onNavigateToAuth,
            onNavigateToSubscriptions = onNavigateToSubscriptions,
            onNavigateToPreviousScreen = onNavigateToPreviousScreen,
            onNavigateToAudioPlayer = onNavigateToAudioPlayer,
            onNavigateToReadSummaryScreen = onNavigateToReadSummaryScreen,
            onNavigateToRecommendedSummaryDetails = onNavigateToRecommendedSummaryDetails,
            onNavigateToPermissionExplainer = onNavigateToPermissionExplainer,
            onNavigateToAIPrompts = onNavigateToAIPrompts
        )
    }
}

fun NavController.toSummaryDetails(
    summaryId: SummaryId,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen
            .SummaryDetails
            .route
            .replace(
                oldValue = "{${NavigationScreen.SummaryDetails.SUMMARY_ID_KEY}}",
                newValue = summaryId.value
            )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
