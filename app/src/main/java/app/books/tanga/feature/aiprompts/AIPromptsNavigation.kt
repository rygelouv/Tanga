package app.books.tanga.feature.aiprompts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import app.books.tanga.entity.SummaryId
import app.books.tanga.navigation.NavigationScreen
import app.books.tanga.utils.sharedViewModel

fun NavGraphBuilder.aiPrompts(
    onNavigateBack: () -> Unit,
    onNavigateToResponse: () -> Unit,
    navController: NavHostController
) {
    navigation(
        route = NavigationScreen.AIPrompts.Graph.route,
        startDestination = NavigationScreen.AIPrompts.List.route,
        arguments = listOf(
            navArgument(NavigationScreen.AIPrompts.SUMMARY_ID_KEY) {
                type = NavType.StringType
            },
        )
    ) {
        composable(
            route = NavigationScreen.AIPrompts.List.route,
        ) { entry ->
            val viewModel = entry.sharedViewModel<AIPromptsViewModel>(navController = navController)
            val summaryId = entry.arguments?.getString(NavigationScreen.AIPrompts.SUMMARY_ID_KEY)!!
            AIPromptsContainer(
                summaryId = SummaryId(summaryId),
                onNavigateBack = onNavigateBack,
                onNavigateToResponse = onNavigateToResponse,
                viewModel = viewModel
            )
        }

        composable(
            route = NavigationScreen.AIPrompts.Response.route,
        ) { entry ->
            val viewModel = entry.sharedViewModel<AIPromptsViewModel>(navController = navController)
            AIPromptsResponseContainer(
                onNavigateBack = onNavigateBack,
                viewModel = viewModel
            )
        }
    }
}

fun NavController.toAIPrompts(
    summaryId: String,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen.AIPrompts.Graph.route.replace(
            oldValue = "{${NavigationScreen.AIPrompts.SUMMARY_ID_KEY}}",
            newValue = summaryId
        )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}

fun NavController.toAiPromptResponse() {
    navigate(NavigationScreen.AIPrompts.Response.route)
}
