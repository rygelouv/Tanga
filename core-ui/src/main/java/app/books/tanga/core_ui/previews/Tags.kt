package app.books.tanga.core_ui.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.core_ui.R
import app.books.tanga.core_ui.components.Tag


@Preview
@Composable
fun TagPreview() {
    Tag(
        text = "Business",
        icon = R.drawable.ic_indicator_mindmap,
        tint = MaterialTheme.colorScheme.primary
    )
}
