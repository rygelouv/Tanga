package app.books.tanga.feature.aiprompts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.coreui.resources.asString
import app.books.tanga.feature.read.ContentHeaderInfo
import app.books.tanga.feature.read.ReadSummaryScreen

@Composable
fun AIPromptsResponseContainer(
    onNavigateBack: () -> Unit,
    viewModel: AIPromptsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getAIPromptResponse()
    }

    AIPromptsResponseScreen(
        state = state,
        onNavigateBack = {
            viewModel.onPromptResponseBackClicked()
            onNavigateBack()
        },
        onFontSizeClick = { viewModel.onFontSizeClicked() },
        onFontScaleChange = { viewModel.onScaleChanged(it) },
    )
}

@Composable
fun AIPromptsResponseScreen(
    state: AIPromptsUiState,
    onNavigateBack: () -> Unit,
    onFontSizeClick: () -> Unit,
    onFontScaleChange: (Float) -> Unit
) {
    val context = LocalContext.current

    state.promptResponseState?.let {
        val contentHeaderInfo = state.selectedPrompt?.let { prompt ->
            ContentHeaderInfo(
                icon = prompt.icon,
                title = prompt.title.asString(context.resources),
            )
        }
        ReadSummaryScreen(
            state = it,
            contentHeaderInfo = contentHeaderInfo,
            shouldShowFavoriteButton = false,
            onNavigateToPreviousScreen = onNavigateBack,
            onNavigateToAudioPlayer = { },
            onToggleFavorite = { },
            onFontSizeClick = onFontSizeClick,
            onFontScaleChange = onFontScaleChange
        )
    }
}
