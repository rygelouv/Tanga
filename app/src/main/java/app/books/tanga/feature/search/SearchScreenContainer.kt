package app.books.tanga.feature.search

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.ShowSnackbarError

@Composable
fun SearchScreenContainer(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToSummary: (SummaryId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(initialValue = null)
    val snackBarHostState = remember { SnackbarHostState() }

    HandleEvents(event = event, snackBarHostState = snackBarHostState, onNavigateToSummary = onNavigateToSummary)

    SearchScreen(
        state = state,
        snackBarHostState = snackBarHostState,
        modifier = modifier,
        onNavigateToPreviousScreen = onNavigateToPreviousScreen,
        onSearch = { viewModel.onSearch(it) },
        onCategorySelect = { viewModel.onCategorySelected(it) },
        onCategoryUnselect = { viewModel.onCategoryUnselected(it) },
        onNavigateToSummary = { viewModel.onNavigateToSummary(it) },
        onRetry = { viewModel.onRetry() }
    )
}

@Composable
private fun HandleEvents(
    event: SearchUiEvent?,
    snackBarHostState: SnackbarHostState,
    onNavigateToSummary: (SummaryId) -> Unit,
) {
    when (event) {
        is SearchUiEvent.ShowSnackError -> {
            ShowSnackbarError(errorInfo = event.error.info, snackbarHostState = snackBarHostState)
        }

        is SearchUiEvent.NavigateTo.ToSummary -> {
            onNavigateToSummary(event.summaryId)
        }

        null -> Unit
    }
}
