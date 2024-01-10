package app.books.tanga.feature.summary.list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.SummaryId
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.summariesByCategory(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToSummary: (SummaryId) -> Unit,
    onNavigateToSearch: () -> Unit
) {
    composable(
        route = NavigationScreen.SummariesByCategory.route,
        arguments = listOf(
            navArgument(NavigationScreen.SummariesByCategory.CATEGORY_ID_KEY) {
                type = NavType.StringType
            },
            navArgument(NavigationScreen.SummariesByCategory.CATEGORY_NAME_KEY) {
                type = NavType.StringType
            },
        )
    ) { backstackEntry ->
        val categoryId = CategoryId(
            value = backstackEntry
                .arguments
                ?.getString(NavigationScreen.SummariesByCategory.CATEGORY_ID_KEY)!!
        )
        val categoryName = backstackEntry
            .arguments
            ?.getString(NavigationScreen.SummariesByCategory.CATEGORY_NAME_KEY)!!
        SummariesByCategoryScreenContainer(
            categoryId = categoryId,
            categoryName = categoryName,
            onNavigateToPreviousScreen = onNavigateToPreviousScreen,
            onNavigateToSummary = onNavigateToSummary,
            onNavigateToSearch = onNavigateToSearch
        )
    }
}

fun NavController.toSummariesByCategory(
    categoryId: CategoryId,
    categoryName: String,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen.SummariesByCategory.route
            .replace(
                oldValue = "{${NavigationScreen.SummariesByCategory.CATEGORY_ID_KEY}}",
                newValue = categoryId.value
            )
            .replace(
                oldValue = "{${NavigationScreen.SummariesByCategory.CATEGORY_NAME_KEY}}",
                newValue = categoryName
            )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
