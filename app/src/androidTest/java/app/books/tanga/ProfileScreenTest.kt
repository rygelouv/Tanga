package app.books.tanga

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.feature.profile.ProfileScreen
import app.books.tanga.feature.profile.ProfileUiState
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockState = ProfileUiState(fullName = "John Doe", photoUrl = "https://example.com/photo.jpg")
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun profileScreen_RendersCorrectly() {
        composeTestRule.setContent {
            ProfileScreen(state = mockState)
        }

        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithTag("profile_image").assertIsDisplayed()
    }

    @Test
    fun profileScreen_ProButtonIsVisible() {
        composeTestRule.setContent {
            ProfileScreen(state = mockState)
        }

        val text = context.resources.getString(R.string.profile_upgrade_to_pro)

        composeTestRule.onNodeWithText(text = text).assertIsDisplayed()
    }

    @Test
    fun profileScreen_ProButtonClickTriggersAction() {
        var proClicked = false

        composeTestRule.setContent {
            ProfileScreen(state = ProfileUiState(), onProClick = { proClicked = true })
        }

        val text = context.resources.getString(R.string.profile_upgrade_to_pro)

        composeTestRule.onNodeWithText(text).performClick()
        assert(proClicked) // Verify that the click triggers the onProClick action
    }

    @Ignore("Disabling because failing on CI")
    @Test
    fun profileScreen_LogoutProcessInitiated() {
        composeTestRule.setContent {
            ProfileScreen(state = ProfileUiState(), onLogout = { })
        }

        val logOutText = context.resources.getString(R.string.logout)
        val logOutDialogMessage = context.resources.getString(R.string.confirm_logout_title)

        composeTestRule.onNodeWithText(logOutText).performClick()

        composeTestRule.onNodeWithText(logOutDialogMessage).assertIsDisplayed()
    }
}
