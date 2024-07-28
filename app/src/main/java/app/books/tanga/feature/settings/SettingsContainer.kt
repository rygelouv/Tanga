package app.books.tanga.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsContainer(
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToDeleteAccount: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(SettingsUiEvent.Empty)

    when (event) {
        is SettingsUiEvent.NavigateTo.Auth -> onNavigateToAuth()
        else -> Unit
    }

    SettingsScreen(
        state = state,
        onLogout = viewModel::onLogout,
        onDismissLogoutConfirmationDialog = viewModel::onDismissLogoutConfirmationDialog,
        onConfirmLogout = viewModel::onConfirmLogout,
        onDeleteAccount = onNavigateToDeleteAccount,
        onNavigateBack = onNavigateBack,
    )
}
