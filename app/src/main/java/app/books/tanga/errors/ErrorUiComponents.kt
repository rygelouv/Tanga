package app.books.tanga.errors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.resources.TextResource
import app.books.tanga.coreui.resources.asString
import app.books.tanga.coreui.theme.LocalSpacing
import kotlinx.coroutines.launch

/**
 * Displays an error message inside a snackbar.
 *
 * This Composable presents an error to the user using a snackbar. The snackbar is displayed for a short duration and
 * then dismissed automatically. The provided `errorInfo` is used to determine the message to display.
 *
 * @param errorInfo The [UiErrorInfo] model containing details about the error to display.
 * @param snackbarHostState The [SnackbarHostState] used to display the snackbar.
 */
@Composable
fun ShowSnackbarError(
    errorInfo: UiErrorInfo,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()
    val message =
        errorInfo.message?.asString(LocalContext.current.resources)
            ?: stringResource(id = R.string.error_message_default)
    val actionLabel = stringResource(id = R.string.ok)

    LaunchedEffect(errorInfo) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
        }
    }
}

/**
 * Displays an error message inside a modal bottom sheet.
 *
 * This Composable presents an error to the user using a modal bottom sheet. When the bottom sheet is dismissed,
 * either by user action or programmatically, the provided `onDismiss` callback is invoked.
 *
 * @param errorInfo The [UiErrorInfo] model containing details about the error to display.
 * @param onDismiss A callback to be invoked when the bottom sheet is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorBottomSheetModal(
    errorInfo: UiErrorInfo,
    onDismiss: () -> Unit
) {
    var openBottomSheet by remember { mutableStateOf(true) }
    val bottomSheetState =
        rememberModalBottomSheetState(
            // Show the full sheet when expanded
            skipPartiallyExpanded = true
        )
    val coroutineScope = rememberCoroutineScope()

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                openBottomSheet = false
                onDismiss()
            },
            sheetState = bottomSheetState
        ) {
            ErrorContent(errorInfo, onClick = {
                coroutineScope
                    .launch { bottomSheetState.hide() }
                    .invokeOnCompletion {
                        if (bottomSheetState.isVisible.not()) {
                            openBottomSheet = false
                            onDismiss()
                        }
                    }
            })
        }
    }
}

/**
 * Error view that can be displayed in a screen or a modal
 *
 * @param errorInfo: The error info to be displayed
 * @param onClick: The function to be executed when the button is clicked.
 */
@Composable
fun ErrorContent(
    errorInfo: UiErrorInfo,
    canRetry: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.small
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current
        Image(
            modifier = Modifier
                .size(230.dp)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = errorInfo.icon ?: R.drawable.graphic_oops_error),
            contentDescription = "empty search illustration"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text =
            errorInfo.title?.asString(context.resources)
                ?: stringResource(id = R.string.error),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text =
            errorInfo.message?.asString(context.resources)
                ?: stringResource(id = R.string.error_message_default),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        TangaButton(
            text = stringResource(id = if (canRetry) R.string.retry else R.string.close),
            onClick = onClick
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ErrorContentPreview() {
    val errorInfo =
        UiErrorInfo(
            title = TextResource.fromText("Error!"),
            message = TextResource.fromText("Something went wrong. Please try again"),
            icon = R.drawable.graphic_oops_error
        )
    ErrorContent(errorInfo = errorInfo) {}
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ErrorBottomSheetModalPreview() {
    val errorInfo =
        UiErrorInfo(
            title = TextResource.fromText("Error!"),
            message = TextResource.fromText("Something went wrong. Please try again"),
            icon = R.drawable.graphic_oops_error
        )
    ErrorBottomSheetModal(errorInfo = errorInfo) {}
}

@Preview
@Composable
fun ShowSnackbarErrorPreview() {
    val errorInfo =
        UiErrorInfo(
            title = TextResource.fromText("Error!"),
            message = TextResource.fromText("Something went wrong. Please try again"),
            icon = R.drawable.graphic_oops_error
        )
    val snackbarHostState = SnackbarHostState()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ShowSnackbarError(errorInfo = errorInfo, snackbarHostState = snackbarHostState)
        }
    }
}
