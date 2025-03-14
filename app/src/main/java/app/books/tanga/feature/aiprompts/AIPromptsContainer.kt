package app.books.tanga.feature.aiprompts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.entity.SummaryId

@Composable
fun AIPromptsContainer(
    summaryId: SummaryId,
    onNavigateBack: () -> Unit,
    viewModel: AIPromptsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getAIPromptsForSummary(summaryId)
    }
    AIPromptsScreen(
        onNavigateBack = onNavigateBack,
        state = state
    )
}
