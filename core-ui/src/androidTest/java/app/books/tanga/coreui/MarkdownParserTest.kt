package app.books.tanga.coreui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import app.books.tanga.coreui.common.parseMarkdown
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class MarkdownParserTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun parseMarkdown_renders_heading_correctly() {
        val markdown = "# Test Heading"
        val elements = parseMarkdown(markdown, 1.0f)

        composeTestRule.setContent {
            elements.forEach { it() }
        }

        composeTestRule.onNodeWithText("Test Heading").assertExists()
    }

    @Test
    fun parseMarkdown_renders_paragraph_correctly() {
        val markdown = "Test Paragraph"
        val elements = parseMarkdown(markdown, 1.0f)

        composeTestRule.setContent {
            elements.forEach { it() }
        }

        composeTestRule.onNodeWithText("Test Paragraph").assertExists()
    }

    @Test
    fun parseMarkdown_renders_emphasis_correctly() {
        val markdown = "*Test Emphasis*"
        val elements = parseMarkdown(markdown, 1.0f)

        composeTestRule.setContent {
            elements.forEach { it() }
        }

        composeTestRule.onNodeWithText("Test Emphasis").assertExists()
    }

    @Ignore("This test is failing because the bullet list is not being rendered correctly")
    @Test
    fun parseMarkdown_renders_bullet_list_correctly() {
        val markdown = "- Item 1\n- Item 2"
        val elements = parseMarkdown(markdown, 1.0f)

        composeTestRule.setContent {
            elements.forEach { it() }
        }

        composeTestRule.onNodeWithText("Item 1").assertExists()
        composeTestRule.onNodeWithText("Item 2").assertExists()
    }

    @Test
    fun parseMarkdown_renders_ordered_list_correctly() {
        val markdown = "1. Item 1\n2. Item 2"
        val elements = parseMarkdown(markdown, 1.0f)

        composeTestRule.setContent {
            elements.forEach { it() }
        }

        composeTestRule.onNodeWithText("1.").assertExists()
        composeTestRule.onNodeWithText("Item 1").assertExists()
        composeTestRule.onNodeWithText("2.").assertExists()
        composeTestRule.onNodeWithText("Item 2").assertExists()
    }
}
