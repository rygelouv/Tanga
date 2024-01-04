package app.books.tanga.feature.summary.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.SummaryId

@Composable
fun SummariesByCategoryScreenContainer(
    categoryId: CategoryId,
    categoryName: String,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToSummary: (SummaryId) -> Unit,
    onNavigateToSearch: () -> Unit,
    viewModel: SummaryByCategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = categoryId) {
        viewModel.loadSummaries(categoryId, categoryName)
    }

    SummariesByCategoryScreen(
        state = state,
        onNavigateToPreviousScreen = onNavigateToPreviousScreen,
        onNavigateToSummary = onNavigateToSummary,
        onNavigateToSearch = onNavigateToSearch
    )
}
