package app.books.tanga.core_ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.core_ui.R
import app.books.tanga.core_ui.components.TangaButtonLeftIcon
import app.books.tanga.core_ui.components.TangaButtonRightIcon

@Preview
@Composable
fun TangaButtonLeftIconPreview() {
    TangaButtonRightIcon(
        text = "Get Started",
        leftIcon = R.drawable.ic_right_arrow,
        onClick = { /*TODO*/ }
    )
}

@Preview
@Composable
fun TangaButtonRightIconPreview() {
    TangaButtonLeftIcon(
        text = "Explore summaries",
        rightIcon = R.drawable.ic_search,
        onClick = { /*TODO*/ }
    )
}