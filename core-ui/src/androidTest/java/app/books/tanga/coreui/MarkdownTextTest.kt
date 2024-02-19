package app.books.tanga.coreui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import app.books.tanga.coreui.components.MarkdownText
import org.junit.Rule
import org.junit.Test

class MarkdownTextTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun markdownText_renders_heading_correctly() {
        val markdown = "# Test Heading"

        composeTestRule.setContent {
            MarkdownText(markdown, 1.0f)
        }

        composeTestRule.onNodeWithText("Test Heading").assertExists()
    }

    @Test
    fun markdownText_renders_paragraph_correctly() {
        val markdown = "Test Paragraph"

        composeTestRule.setContent {
            MarkdownText(markdown, 1.0f)
        }

        composeTestRule.onNodeWithText("Test Paragraph").assertExists()
    }

    @Test
    fun markdownText_renders_emphasis_correctly() {
        val markdown = "*Test Emphasis*"

        composeTestRule.setContent {
            MarkdownText(markdown, 1.0f)
        }

        composeTestRule.onNodeWithText("Test Emphasis").assertExists()
    }

    @Test
    fun markdownText_renders_ordered_list_correctly() {
        val markdown = "1. Item 1\n2. Item 2"

        composeTestRule.setContent {
            MarkdownText(markdown, 1.0f)
        }

        composeTestRule.onNodeWithText("1.").assertExists()
        composeTestRule.onNodeWithText("Item 1").assertExists()
        composeTestRule.onNodeWithText("2.").assertExists()
        composeTestRule.onNodeWithText("Item 2").assertExists()
    }
}
