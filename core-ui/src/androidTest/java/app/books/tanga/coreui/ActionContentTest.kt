package app.books.tanga.coreui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import app.books.tanga.coreui.components.ActionContent
import app.books.tanga.coreui.components.ActionData
import app.books.tanga.coreui.components.Button
import app.books.tanga.coreui.resources.TextResource
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ActionContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testActionContentBasicRendering() {
        composeTestRule.setContent {
            ActionContent(
                data = ActionData(
                    title = TextResource.fromText("Expected Title"),
                    description = TextResource.fromText("Expected Description"),
                    icon = R.drawable.graphic_book_search,
                    mainButton = Button(
                        text = TextResource.fromText("Main Button Text"),
                        onClick = {}
                    )
                )
            )
        }

        // Check if the main components are displayed
        composeTestRule.onNodeWithContentDescription("action content image").assertIsDisplayed()
        composeTestRule.onNodeWithText("Expected Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Expected Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Main Button Text").assertIsDisplayed()
    }

    @Test
    fun testActionContentWithSecondaryButton() {
        composeTestRule.setContent {
            ActionContent(
                data = ActionData(
                    title = TextResource.fromText("Expected Title"),
                    description = TextResource.fromText("Expected Description"),
                    icon = R.drawable.graphic_book_search,
                    mainButton = Button(
                        text = TextResource.fromText("Main Button Text"),
                        onClick = {}
                    ),
                    secondaryButton = Button(
                        text = TextResource.fromText("Secondary Button Text"),
                        onClick = {}
                    )
                )
            )
        }

        // Assert that the secondary button is displayed
        composeTestRule.onNodeWithText("Secondary Button Text").assertIsDisplayed()
    }

    @Test
    fun testActionContentTextContent() {
        val title = "Test Title"
        val description = "Test Description"

        composeTestRule.setContent {
            ActionContent(
                data = ActionData(
                    title = TextResource.fromText("Test Title"),
                    description = TextResource.fromText("Test Description"),
                    icon = R.drawable.graphic_book_search,
                    mainButton = Button(TextResource.fromText("Main Button")) { }
                )
            )
        }

        // Assert that title and description are correct
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
    }

    @Test
    fun testActionContentButtonClicks() {
        var mainButtonClicked = false
        var secondaryButtonClicked = false

        composeTestRule.setContent {
            ActionContent(
                data = ActionData(
                    title = TextResource.fromText("Expected Title"),
                    description = TextResource.fromText("Expected Description"),
                    icon = R.drawable.graphic_book_search,
                    mainButton = Button(TextResource.fromText("Main Button")) { mainButtonClicked = true },
                    secondaryButton = Button(TextResource.fromText("Secondary Button")) {
                        secondaryButtonClicked = true
                    }
                )
            )
        }

        // Perform click actions
        composeTestRule.onNodeWithText("Main Button").performClick()
        composeTestRule.onNodeWithText("Secondary Button").performClick()

        assertTrue(mainButtonClicked)
        assertTrue(secondaryButtonClicked)
    }
}
