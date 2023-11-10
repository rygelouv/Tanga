package app.books.tanga

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.feature.profile.LogoutDialog
import org.junit.Rule
import org.junit.Test

class LogoutDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun logoutDialog_ConfirmButtonClickTriggersAction() {
        var confirmClicked = false

        composeTestRule.setContent {
            TangaTheme {
                LogoutDialog(onDismiss = {}, onConfirm = { confirmClicked = true })
            }
        }

        // Simulate user click on the Confirm button
        composeTestRule.onNodeWithTag("confirm_button").performClick()

        // Verify that the click triggers the onConfirm action
        assert(confirmClicked)
    }

    @Test
    fun logoutDialog_DismissButtonClickTriggersAction() {
        var dismissClicked = false

        composeTestRule.setContent {
            TangaTheme {
                LogoutDialog(onDismiss = { dismissClicked = true }, onConfirm = {})
            }
        }

        // Simulate user click on the Dismiss (Cancel) button
        composeTestRule.onNodeWithTag("dismiss_button").performClick()

        // Verify that the click triggers the onDismiss action
        assert(dismissClicked)
    }
}
