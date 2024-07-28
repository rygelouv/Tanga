package app.books.tanga.feature.deleteaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.components.ActionContent
import app.books.tanga.coreui.components.ActionData
import app.books.tanga.coreui.components.Button
import app.books.tanga.coreui.components.SystemBarsVisibility
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.resources.TextResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountScreen(
    onNavigateBack: () -> Unit,
    onDeleteAccount: () -> Unit,
    onConfirmDeleteAccount: () -> Unit,
    onDismissConfirmationDialog: () -> Unit,
    state: DeleteAccountUiState,
    modifier: Modifier = Modifier
) {
    SystemBarsVisibility(
        statusBarColor = MaterialTheme.colorScheme.onPrimary,
        statusBarVisible = true,
        navigationBarVisible = true
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(id = TangaIcons.LeftArrow),
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            contentDescription = "back navigation"
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val actionData = ActionData(
                title = TextResource.fromText(stringResource(id = R.string.delete_account_title)),

                description = TextResource.fromText(stringResource(id = R.string.delete_account_description)),
                icon = app.books.tanga.coreui.R.drawable.graphic_man_thinking,
                mainButton = Button(
                    text = TextResource.fromStringId(R.string.delete_account_cancel_button_text),
                    onClick = onNavigateBack
                ),
                secondaryButton = Button(
                    text = TextResource.fromStringId(R.string.delete_account),
                    onClick = onDeleteAccount
                )
            )
            ActionContent(
                data = actionData,
                titleTextColor = MaterialTheme.colorScheme.error,
                bigSpacingBeforeButtons = true,
                shouldCenterDescriptionText = false
            )

            if (state.showConfirmationDialog) {
                DeleteAccountDialog(
                    onDismiss = { onDismissConfirmationDialog() },
                    onConfirm = { onConfirmDeleteAccount() }
                )
            }
        }
    }
}

@Composable
fun DeleteAccountDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = onConfirm, modifier = Modifier.testTag("confirm_button")) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.testTag("dismiss_button")) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        title = { Text(text = stringResource(id = R.string.delete_account_dialog_confirm_title)) },
        text = { Text(text = stringResource(id = R.string.delete_account_dialog_confirm_message)) }
    )
}
