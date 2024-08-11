package app.books.tanga.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.CategoryId

@Composable
fun HomeContainer(
    onSearch: () -> Unit,
    onProfilePictureClick: () -> Unit,
    onNavigateToSummariesByCategory: (CategoryId, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onSummaryClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        onSearch = onSearch,
        onProfilePictureClick = onProfilePictureClick,
        onSummaryClick = { summaryId -> onSummaryClick(summaryId) },
        onRetry = { viewModel.onRetry() },
        state = state,
        onNavigateToSummariesByCategory = { categoryId, categoryName ->
            onNavigateToSummariesByCategory(categoryId, categoryName)
        }
    )
}
