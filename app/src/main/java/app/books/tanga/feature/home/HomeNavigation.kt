package app.books.tanga.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.entity.CategoryId
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.home(
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSummaryDetails: (summaryId: String) -> Unit,
    onNavigateToSummariesByCategory: (categoryId: CategoryId, categoryName: String) -> Unit
) {
    composable(route = NavigationScreen.BottomBarScreen.Home.route) {
        HomeContainer(
            onSearch = onNavigateToSearch,
            onProfilePictureClick = onNavigateToProfile,
            onSummaryClick = { summaryId -> onNavigateToSummaryDetails(summaryId) },
            onNavigateToSummariesByCategory = { categoryId, categoryName ->
                onNavigateToSummariesByCategory(categoryId, categoryName)
            }
        )
    }
}
