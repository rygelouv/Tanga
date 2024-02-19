package app.books.tanga.coreui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.coreui.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.Button
import app.books.tanga.coreui.components.SearchButton
import app.books.tanga.coreui.components.SummaryActionButton
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.components.TangaButtonLeftIcon
import app.books.tanga.coreui.components.TangaButtonRightIcon
import app.books.tanga.coreui.components.TangaFloatingActionButton
import app.books.tanga.coreui.components.TangaLinedButton
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.resources.TextResource
import app.books.tanga.coreui.theme.TangaTheme

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TangaButtonPreview() {
    TangaButton(
        text = "Get Started",
        onClick = { }
    )
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TangaLinedButtonPreview() {
    TangaLinedButton(
        text = "Get Started",
        onClick = { },
    )
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TangaButtonLeftIconPreview() {
    TangaButtonRightIcon(
        text = "Get Started",
        leftIcon = R.drawable.ic_right_arrow,
        onClick = { }
    )
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TangaButtonRightIconPreview() {
    TangaButtonLeftIcon(
        text = "Explore summaries",
        rightIcon = R.drawable.ic_search,
        onClick = { }
    )
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SummaryActionButtonPreview() {
    SummaryActionButton(text = "Read", icon = TangaIcons.IndicatorRead) {}
}

@Preview(device = "id:pixel_5")
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SearchButtonPreview() {
    TangaTheme {
        SearchButton(onSearch = { /*TODO*/ })
    }
}

@Preview(device = "id:pixel_5")
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TangaFloatingActionButtonPreview() {
    TangaTheme {
        TangaFloatingActionButton(
            button = Button(
                text = TextResource.fromText("Play"),
                icon = R.drawable.ic_indicator_listen,
                onClick = { }
            )
        )
    }
}
