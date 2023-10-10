package app.books.tanga.feature.listen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.playSummaryAudio(onBackClicked: () -> Unit) {
    composable(
        route = NavigationScreen.PlaySummaryAudio.route,
        arguments = listOf(
            navArgument(NavigationScreen.SummaryDetails.SUMMARY_ID_KEY) {
                type = NavType.StringType
            },
        )
    ) { backStackEntry ->
        PlaySummaryAudioScreen(
            summaryId = backStackEntry
                .arguments
                ?.getString(NavigationScreen.SummaryDetails.SUMMARY_ID_KEY)!!,
            onBackClicked = onBackClicked,
        )
    }
}

fun NavController.toPlaySummaryAudio(
    summaryId: String,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true,
) {
    navigate(
        route = NavigationScreen
            .PlaySummaryAudio
            .route
            .replace(
                oldValue = "{${NavigationScreen.SummaryDetails.SUMMARY_ID_KEY}}",
                newValue = summaryId,
            ),
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
