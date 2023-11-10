package app.books.tanga

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.feature.profile.ProfileAction
import app.books.tanga.feature.profile.ProfileContentAction
import org.junit.Rule
import org.junit.Test

class ProfileActionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun profileContentAction_ContactRendersCorrectly() {
        composeTestRule.setContent {
            TangaTheme {
                ProfileContentAction(action = ProfileAction.CONTACT)
            }
        }

        val text = context.getString(ProfileAction.CONTACT.text)

        // Assertions for CONTACT action
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("action icon").assertIsDisplayed()
    }

    @Test
    fun profileContentAction_ContactClickTriggersAction() {
        var clicked = false

        composeTestRule.setContent {
            TangaTheme {
                ProfileContentAction(action = ProfileAction.CONTACT, onClick = { clicked = true })
            }
        }

        composeTestRule.onNodeWithTag("profile_action").assertHasClickAction()
        composeTestRule.onNodeWithText("Contact Us").performClick()

        // Verify that the click triggers the onClick action
        assert(clicked)
    }
}
