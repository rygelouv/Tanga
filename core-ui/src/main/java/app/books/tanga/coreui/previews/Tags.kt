package app.books.tanga.coreui.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.coreui.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.Tag

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TagPreview() {
    Tag(
        text = "Business",
        icon = R.drawable.ic_indicator_graphic,
        tint = MaterialTheme.colorScheme.primary
    )
}
