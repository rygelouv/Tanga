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
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val data = BottomSheetData(
        actionData = ActionData(
            title = TextResource.fromText(stringResource(id = R.string.auth_suggestion_title)),
            description = TextResource.fromText(stringResource(id = R.string.auth_suggestion_description)),
            icon = app.books.tanga.coreui.R.drawable.graphic_login_bro,
            mainButton = Button(
                text = TextResource.fromText("Sign in"),
                onClick = { }
            ),
            secondaryButton = Button(
                text = TextResource.fromText("Not Now"),
                onClick = { }
            )
        ),
        onDismiss = onDismiss
    )

    TangaBottomSheet(
        data = data,
        modifier = modifier.testTag("auth_suggestion_bottom_sheet")
    )
}
