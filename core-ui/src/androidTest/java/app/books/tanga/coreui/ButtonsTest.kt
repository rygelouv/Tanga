package app.books.tanga.coreui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import app.books.tanga.coreui.components.Button
import app.books.tanga.coreui.components.SearchButton
import app.books.tanga.coreui.components.SummaryActionButton
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.components.TangaButtonLeftIcon
import app.books.tanga.coreui.components.TangaButtonRightIcon
import app.books.tanga.coreui.components.TangaFloatingActionButton
import app.books.tanga.coreui.components.TangaLinedButton
import app.books.tanga.coreui.components.TangaPlayAudioFab
import app.books.tanga.coreui.resources.TextResource
import junit.framework.TestCase.assertFalse
import org.junit.Rule
import org.junit.Test

class ButtonsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tangaButton_displaysTextAndHandlesClick() {
        var clicked = false
        composeTestRule.setContent {
            TangaButton(text = "Button", onClick = { clicked = true })
        }

        composeTestRule.onNodeWithText("Button").performClick()
        assert(clicked)
    }

    @Test
    fun tangaLinedButton_displaysTextAndHandlesClick() {
        var clicked = false
        composeTestRule.setContent {
            TangaLinedButton(text = "Button", onClick = { clicked = true })
        }

        composeTestRule.onNodeWithText("Button").performClick()
        assert(clicked)
    }

    @Test
    fun tangaButtonRightIcon_displaysTextAndHandlesClick() {
        var clicked = false
        composeTestRule.setContent {
            TangaButtonRightIcon(text = "Button", leftIcon = R.drawable.ic_search, onClick = { clicked = true })
        }

        composeTestRule.onNodeWithTag("button_right_icon", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Button").performClick()
        assert(clicked)
    }

    @Test
    fun tangaButtonLeftIcon_displaysTextAndHandlesClick() {
        var clicked = false
        composeTestRule.setContent {
            TangaButtonLeftIcon(text = "Button", rightIcon = R.drawable.ic_search, onClick = { clicked = true })
        }

        composeTestRule.onNodeWithTag("button_left_icon", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Button").performClick()
        assert(clicked)
    }

    @Test
    fun summaryActionButton_displaysTextAndHandlesClick() {
        var clicked = false
        composeTestRule.setContent {
            SummaryActionButton(text = "Button", icon = R.drawable.ic_search, onClick = { clicked = true })
        }

        composeTestRule.onNodeWithTag("summary_action_button_icon", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("summary_action_button").assertHasClickAction()
        composeTestRule.onNodeWithTag("summary_action_button").performClick()
        assert(clicked)
    }

    @Test
    fun summaryActionButton_behavesCorrectly_whenDisabled() {
        var clicked = false
        composeTestRule.setContent {
            SummaryActionButton(
                text = "Button",
                icon = R.drawable.ic_search,
                onClick = { clicked = true },
                enabled = false
            )
        }
        composeTestRule.onNodeWithTag("summary_action_button").assertHasNoClickAction()
        assertFalse(clicked)
    }

    @Test
    fun searchButton_handlesClick() {
        var clicked = false
        composeTestRule.setContent {
            SearchButton(onSearch = { clicked = true })
        }

        composeTestRule.onNodeWithTag("search_icon", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Search").performClick()
        assert(clicked)
    }

    @Test
    fun tangaPlayAudioFab_handlesClick() {
        var clicked = false
        composeTestRule.setContent {
            TangaPlayAudioFab(onNavigateToAudioPlayer = { clicked = true })
        }

        composeTestRule.onNodeWithTag("fab_button").performClick()

        assert(clicked)
    }

    @Test
    fun tangaFloatingActionButton_handlesClick() {
        var clicked = false
        val button = Button(text = TextResource.fromText("Button"), onClick = { clicked = true })
        composeTestRule.setContent {
            TangaFloatingActionButton(button = button)
        }

        composeTestRule.onNodeWithTag("fab_button").performClick()

        assert(clicked)
    }
}
