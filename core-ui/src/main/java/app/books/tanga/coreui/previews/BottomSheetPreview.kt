package app.books.tanga.coreui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.coreui.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.ActionContent
import app.books.tanga.coreui.components.ActionData
import app.books.tanga.coreui.components.BottomSheetData
import app.books.tanga.coreui.components.Button
import app.books.tanga.coreui.components.TangaBottomSheet
import app.books.tanga.coreui.components.TangaBottomSheetContainer
import app.books.tanga.coreui.resources.TextResource

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TangaBottomSheetPreview() {
    TangaBottomSheet(
        data = BottomSheetData(
            actionData = ActionData(
                title = TextResource.fromText("Action screen title"),
                description = TextResource.fromText(
                    "Description, lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua."
                ),
                icon = R.drawable.graphic_book_lover,
                mainButton = Button(
                    text = TextResource.fromText("Sign in"),
                    onClick = { }
                ),
                secondaryButton = Button(
                    text = TextResource.fromText("Not Now"),
                    onClick = { }
                )
            )
        )
    )
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TangaBottomSheetContainerPreview() {
    TangaBottomSheetContainer(
        content = {
            ActionContent(
                data = ActionData(
                    title = TextResource.fromText("Action screen title"),
                    description = TextResource.fromText(
                        "Description, lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                            "incididunt ut labore et dolore magna aliqua."
                    ),
                    icon = R.drawable.graphic_book_lover,
                    mainButton = Button(
                        text = TextResource.fromText("Sign in"),
                        onClick = { }
                    ),
                    secondaryButton = Button(
                        text = TextResource.fromText("Not Now"),
                        onClick = { }
                    )
                )
            )
        },
        onDismiss = { }
    )
}
