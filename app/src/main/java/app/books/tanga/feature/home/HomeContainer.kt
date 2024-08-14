package app.books.tanga.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.SummaryId

@Composable
fun HomeContainer(
    onSearch: () -> Unit,
    onProfilePictureClick: () -> Unit,
    onNavigateToSummariesByCategory: (CategoryId, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onSummaryClick: (SummaryId) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        onSearch = {
            viewModel.onSearchClick()
            onSearch()
        },
        onProfilePictureClick = {
            viewModel.onProfilePictureClick()
            onProfilePictureClick()
        },
        onSummaryClick = { summaryId ->
            viewModel.onSummaryItemClick(summaryId)
            onSummaryClick(summaryId)
        },
        onRetry = { viewModel.onRetry() },
        state = state,
        onNavigateToSummariesByCategory = { categoryId, categoryName ->
            viewModel.onSeeAllClick(categoryId)
            onNavigateToSummariesByCategory(categoryId, categoryName)
        }
    )
}
