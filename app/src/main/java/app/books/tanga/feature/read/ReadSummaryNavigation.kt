package app.books.tanga.feature.read

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.entity.SummaryId
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.readSummaryScreen(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToPricingPlans: () -> Unit
) {
    composable(
        route = NavigationScreen.ReadSummary.route,
        arguments = listOf(
            navArgument(NavigationScreen.ReadSummary.SUMMARY_ID_KEY) {
                type = NavType.StringType
            },
        )
    ) { backstackEntry ->
        val summaryId = backstackEntry.arguments?.getString(NavigationScreen.ReadSummary.SUMMARY_ID_KEY)!!
        ReadSummaryScreenContainer(
            summaryId = SummaryId(summaryId),
            onNavigateToPreviousScreen = onNavigateToPreviousScreen,
            onNavigateToAudioPlayer = onNavigateToAudioPlayer,
            onNavigateToPricingPlans = onNavigateToPricingPlans
        )
    }
}

fun NavController.toReadSummaryScreen(
    summaryId: String,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen.ReadSummary.route.replace(
            oldValue = "{${NavigationScreen.ReadSummary.SUMMARY_ID_KEY}}",
            newValue = summaryId
        )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
