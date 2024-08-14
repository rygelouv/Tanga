package app.books.tanga.feature.library

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.entity.SummaryId
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.library(
    onNavigateToSearch: () -> Unit,
    onNavigateToSummaryDetails: (summaryId: SummaryId) -> Unit
) {
    composable(route = NavigationScreen.BottomBarScreen.Library.route) {
        LibraryScreen(
            onExploreButtonClick = onNavigateToSearch,
            onFavoriteClick = { summaryId -> onNavigateToSummaryDetails(summaryId) }
        )
    }
}
