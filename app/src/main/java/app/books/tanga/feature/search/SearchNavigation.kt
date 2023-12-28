package app.books.tanga.feature.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.entity.SummaryId
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.search(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToSummary: (SummaryId) -> Unit
) {
    composable(route = NavigationScreen.Search.route) {
        SearchScreenContainer(
            onNavigateToPreviousScreen = onNavigateToPreviousScreen,
            onNavigateToSummary = onNavigateToSummary
        )
    }
}

fun NavController.toSearch(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.Search.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
