package app.books.tanga.feature.graphic

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.entity.SummaryId
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.graphicSummaryScreen(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    onNavigateToPricingPlans: () -> Unit
) {
    composable(
        route = NavigationScreen.GraphicSummary.route,
        arguments = listOf(
            navArgument(NavigationScreen.GraphicSummary.SUMMARY_ID_KEY) {
                type = NavType.StringType
            },
        )
    ) { backstackEntry ->
        val summaryId = backstackEntry.arguments?.getString(NavigationScreen.GraphicSummary.SUMMARY_ID_KEY)!!
        GraphicSummaryScreenContainer(
            summaryId = SummaryId(summaryId),
            onNavigateToPreviousScreen = onNavigateToPreviousScreen,
            onNavigateToAudioPlayer = onNavigateToAudioPlayer,
            onNavigateToPricingPlans = onNavigateToPricingPlans
        )
    }
}

fun NavController.toGraphicSummaryScreen(
    summaryId: String,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen.GraphicSummary.route.replace(
            oldValue = "{${NavigationScreen.GraphicSummary.SUMMARY_ID_KEY}}",
            newValue = summaryId
        )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
