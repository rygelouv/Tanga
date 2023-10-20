package app.books.tanga.coreui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.coreui.R
import app.books.tanga.coreui.components.SummaryActionButton
import app.books.tanga.coreui.components.TangaButtonLeftIcon
import app.books.tanga.coreui.components.TangaButtonRightIcon
import app.books.tanga.coreui.icons.TangaIcons

@Preview
@Composable
private fun TangaButtonLeftIconPreview() {
    TangaButtonRightIcon(
        text = "Get Started",
        leftIcon = R.drawable.ic_right_arrow,
        onClick = { }
    )
}

@Preview
@Composable
private fun TangaButtonRightIconPreview() {
    TangaButtonLeftIcon(
        text = "Explore summaries",
        rightIcon = R.drawable.ic_search,
        onClick = { }
    )
}

@Preview
@Composable
private fun SummaryActionButtonPreview() {
    SummaryActionButton(text = "Read", icon = TangaIcons.IndicatorRead) {}
}
