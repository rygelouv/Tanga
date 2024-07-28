package app.books.tanga.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import app.books.tanga.R
import app.books.tanga.coreui.components.ActionData
import app.books.tanga.coreui.components.BottomSheetData
import app.books.tanga.coreui.components.Button
import app.books.tanga.coreui.components.TangaBottomSheet
import app.books.tanga.coreui.resources.TextResource

@Composable
fun AuthSuggestionBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToAuth: () -> Unit
) {
    val data = BottomSheetData(
        actionData = ActionData(
            title = TextResource.fromText(stringResource(id = R.string.auth_suggestion_title)),
            description = TextResource.fromText(stringResource(id = R.string.auth_suggestion_description)),
            icon = app.books.tanga.coreui.R.drawable.graphic_login_bro,
            mainButton = Button(
                text = TextResource.fromStringId(R.string.auth_sign_in_button_text),
                onClick = onNavigateToAuth
            ),
            secondaryButton = Button(
                text = TextResource.fromStringId(R.string.auth_suggestion_dismiss_button_text),
                onClick = onDismiss
            )
        ),
        onDismiss = onDismiss
    )

    TangaBottomSheet(
        data = data,
        modifier = modifier.testTag("auth_suggestion_bottom_sheet")
    )
}
