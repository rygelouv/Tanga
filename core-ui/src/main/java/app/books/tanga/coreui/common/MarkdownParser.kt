package app.books.tanga.coreui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import app.books.tanga.coreui.theme.LocalSpacing
import org.commonmark.node.AbstractVisitor
import org.commonmark.node.BulletList
import org.commonmark.node.Emphasis
import org.commonmark.node.Heading
import org.commonmark.node.ListItem
import org.commonmark.node.OrderedList
import org.commonmark.node.Paragraph
import org.commonmark.parser.Parser
import org.commonmark.renderer.text.TextContentRenderer

fun parseMarkdown(markdown: String, fontScaleFactor: Float): List<@Composable () -> Unit> {
    val elements = mutableListOf<@Composable () -> Unit>()

    val parser = Parser.builder().build()
    val document = parser.parse(markdown)
    val rendererVisitor = MarkdownRendererVisitor(elements, fontScaleFactor)

    document.accept(rendererVisitor)

    return elements
}

class MarkdownRendererVisitor(
    private val elements: MutableList<@Composable () -> Unit>,
    private val scaleFactor: Float
) : AbstractVisitor() {
    private val renderer: TextContentRenderer = TextContentRenderer.builder().build()

    private fun scaledTextStyle(fontSize: TextUnit): TextStyle = TextStyle(
        color = Color.White,
        fontSize = fontSize * scaleFactor,
        lineHeight = 32.sp * scaleFactor,
        letterSpacing = 0.21.sp * scaleFactor,
        fontWeight = FontWeight.Normal,
    )

    // bodyLarge size, we're not in a composable so we can't use MaterialTheme.typography
    private val textStyle = scaledTextStyle(16.sp)

    override fun visit(heading: Heading?) {
        val text = renderer.render(heading)
        val level = heading?.level
        elements.add {
            if (level != null) {
                val style = when (level) {
                    1 -> MaterialTheme.typography.headlineSmall
                    2 -> MaterialTheme.typography.titleLarge
                    3 -> MaterialTheme.typography.titleMedium
                    else -> MaterialTheme.typography.titleSmall
                }
                val scaledStyle = style.copy(
                    fontSize = (style.fontSize) * scaleFactor,
                    lineHeight = style.lineHeight * scaleFactor,
                    letterSpacing = style.letterSpacing * scaleFactor
                )
                Text(
                    color = Color.White,
                    text = text,
                    style = scaledStyle,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    override fun visit(paragraph: Paragraph?) {
        val text = renderer.render(paragraph)

        elements.add {
            Text(
                text = text,
                style = textStyle
            )
        }
    }

    override fun visit(bulletList: BulletList?) {
        val items = mutableListOf<String>()
        val marker = bulletList?.bulletMarker
        bulletList?.accept(object : AbstractVisitor() {
            override fun visit(listItem: ListItem?) {
                val text = renderer.render(listItem)
                items.add(text)
            }
        })

        elements.add {
            Column {
                items.forEach { item ->
                    Text(text = "$marker $item", color = Color.White)
                }
            }
        }
    }

    override fun visit(orderedList: OrderedList?) {
        val items = mutableListOf<String>()
        val delimiter = orderedList?.delimiter
        orderedList?.accept(object : AbstractVisitor() {
            override fun visit(listItem: ListItem?) {
                listItem?.accept(object : AbstractVisitor() {
                    override fun visit(paragraph: Paragraph?) {
                        val text = renderer.render(paragraph)
                        items.add(text)
                    }
                })
            }
        })

        elements.add {
            Column {
                items.forEachIndexed { index, item ->
                    Row {
                        Spacer(modifier = Modifier.width(LocalSpacing.current.large))
                        Text(text = "${index + 1}$delimiter", style = textStyle, color = Color.White)
                        Spacer(modifier = Modifier.width(LocalSpacing.current.small))
                        Text(text = item, style = textStyle)
                    }
                    Spacer(modifier = Modifier.height(LocalSpacing.current.large))
                }
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        }
    }

    override fun visit(emphasis: Emphasis?) {
        val text = renderer.render(emphasis)
        elements.add {
            Text(text = text, style = textStyle, fontStyle = FontStyle.Italic)
        }
    }
}
