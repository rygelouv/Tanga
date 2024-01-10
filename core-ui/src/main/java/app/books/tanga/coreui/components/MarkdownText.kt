package app.books.tanga.coreui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.books.tanga.coreui.common.parseMarkdown

@Composable
fun MarkdownText(
    markdownText: String,
    textScale: Float,
    modifier: Modifier = Modifier
) {
    val elements = parseMarkdown(markdownText, textScale)
    Column(
        modifier = modifier
    ) {
        elements.forEach { element ->
            element()
        }
    }
}
