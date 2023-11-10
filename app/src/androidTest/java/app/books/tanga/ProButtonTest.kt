package app.books.tanga

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.feature.profile.ProButton
import org.junit.Rule
import org.junit.Test

class ProButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val text = context.resources.getString(R.string.profile_upgrade_to_pro)

    @Test
    fun proButton_RendersCorrectly() {
        composeTestRule.setContent {
            TangaTheme {
                ProButton()
            }
        }

        composeTestRule.onNodeWithText(text).assertIsDisplayed()

        // Verify that the icon is displayed
        composeTestRule.onNodeWithContentDescription("action icon").assertIsDisplayed()

        // Verify that the button is clickable
        composeTestRule.onNodeWithTag("pro_button").assertHasClickAction()
    }

    @Test
    fun proButton_ClickTriggersAction() {
        var clicked = false

        composeTestRule.setContent {
            TangaTheme {
                ProButton(onClick = { clicked = true })
            }
        }

        // Simulate user click on the ProButton
        composeTestRule.onNodeWithText(text).performClick()

        // Verify that the click triggers the onClick action
        assert(clicked)
    }
}
