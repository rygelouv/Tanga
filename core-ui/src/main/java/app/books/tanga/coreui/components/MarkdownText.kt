package app.books.tanga.coreui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.books.tanga.coreui.common.parseMarkdown

@Composable
fun MarkdownText(
    markdownText: String,
    textScale: Float,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val elements = parseMarkdown(markdownText, textScale, textColor)
    Column(
        modifier = modifier
    ) {
        elements.forEach { element ->
            element()
        }
    }
}
