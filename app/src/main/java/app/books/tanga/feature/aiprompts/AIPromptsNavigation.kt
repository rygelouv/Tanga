package app.books.tanga.feature.aiprompts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.entity.SummaryId
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.aiPrompts(
    onNavigateBack: () -> Unit
) {
    composable(
        route = NavigationScreen.AIPrompts.route,
        arguments = listOf(
            navArgument(NavigationScreen.AIPrompts.SUMMARY_ID_KEY) {
                type = NavType.StringType
            },
        )
    ) {
        val summaryId = it.arguments?.getString(NavigationScreen.AIPrompts.SUMMARY_ID_KEY)!!
        AIPromptsContainer(
            summaryId = SummaryId(summaryId),
            onNavigateBack = onNavigateBack
        )
    }
}

fun NavController.toAIPrompts(
    summaryId: String,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen.AIPrompts.route.replace(
            oldValue = "{${NavigationScreen.AIPrompts.SUMMARY_ID_KEY}}",
            newValue = summaryId
        )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
