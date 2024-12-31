package app.books.tanga.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.books.tanga.BuildConfig
import app.books.tanga.R
import app.books.tanga.coreui.components.SimpleTopBar
import app.books.tanga.coreui.components.TangaLinedButton
import app.books.tanga.feature.audioplayer.miniplayer.MiniPlayerAwareSpacer
import app.books.tanga.feature.profile.LogoutDialog
import app.books.tanga.feature.profile.ProfileAction
import app.books.tanga.feature.profile.ProfileContentAction

@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onLogout: () -> Unit,
    onDismissLogoutConfirmationDialog: () -> Unit,
    onConfirmLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SimpleTopBar(onNavigateBack)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                ProfileContentAction(action = ProfileAction.LOGOUT) {
                    onLogout()
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(id = R.string.tanga_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.height(14.dp))

            TangaLinedButton(
                text = stringResource(id = R.string.delete_account),
                textColor = MaterialTheme.colorScheme.error,
                containerColor = Color.Transparent,
                onClick = onDeleteAccount
            )

            MiniPlayerAwareSpacer()

            if (state.showLogoutConfirmationDialog) {
                LogoutDialog(
                    onDismiss = onDismissLogoutConfirmationDialog,
                    onConfirm = onConfirmLogout
                )
            }
        }
    }
}
