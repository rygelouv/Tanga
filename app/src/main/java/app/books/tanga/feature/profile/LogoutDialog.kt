package app.books.tanga.feature.profile

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.R

@Composable
fun LogoutDialog(
    openDialog: Boolean,
    onConfirm: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {}) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            title = { Text(text = stringResource(id = R.string.confirm_logout_title)) },
            text = { Text(text = stringResource(id = R.string.confirm_logout_message)) }
        )
    }
}

@Composable
@Preview
fun LogoutDialogPreview() {
    LogoutDialog(false) {

    }
}