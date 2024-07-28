package app.books.tanga.feature.deleteaccount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DeleteAccountContainer(
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit,
    viewModel: DeleteAccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(DeleteAccountUiEvent.Empty)

    when (event) {
        is DeleteAccountUiEvent.NavigateTo.Auth -> onNavigateToAuth()
        else -> Unit
    }

    DeleteAccountScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onDeleteAccount = viewModel::onDeleteAccount,
        onConfirmDeleteAccount = viewModel::onDeleteConfirmAccount,
        onDismissConfirmationDialog = viewModel::onDismissConfirmationDialog
    )
}
