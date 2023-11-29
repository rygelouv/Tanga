package app.books.tanga.profile

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.R
import app.books.tanga.feature.profile.ProfileScreen
import app.books.tanga.feature.profile.ProfileUiState
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockStateWithPermanentUser = ProfileUiState(
        fullName = "John Doe",
        photoUrl = "https://example.com/photo.jpg"
    )
    private val mockStateWithAnonymousUser = ProfileUiState(
        fullName = "Anonymous",
        photoUrl = null,
        isAnonymous = true
    )

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun profileScreen_RendersCorrectlyWithAnonymousUser() {
        composeTestRule.setContent {
            ProfileScreen(
                state = mockStateWithAnonymousUser,
                onLoginClick = { },
                onLogoutClick = { },
            )
        }

        composeTestRule.onNodeWithText("Anonymous").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.profile_create_account)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.profile_upgrade_to_pro)).assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.profile_create_account)).assertHasClickAction()
    }

    @Test
    fun profileScreen_DoesNotShowLogoutWhenUserIsAnonymous() {
        composeTestRule.setContent {
            ProfileScreen(
                state = mockStateWithAnonymousUser,
                onLoginClick = { },
                onLogoutClick = { },
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.logout)).assertDoesNotExist()
    }

    @Test
    fun profileScreen_showsLogoutWhenUserIsLoggedIn() {
        composeTestRule.setContent {
            ProfileScreen(
                state = mockStateWithPermanentUser,
                onLoginClick = { },
                onLogoutClick = { },
            )
        }

        val logoutText = context.getString(R.string.logout)

        composeTestRule.onNodeWithText(logoutText).assertIsDisplayed()
        composeTestRule.onNodeWithText(logoutText).assertHasClickAction()
        composeTestRule.onNodeWithText(logoutText).performClick()
    }

    @Test
    fun profileScreen_RendersCorrectlyWhenUserIsLoggedIn() {
        composeTestRule.setContent {
            ProfileScreen(
                state = mockStateWithPermanentUser,
                onLoginClick = { },
                onLogoutClick = { },
            )
        }

        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithTag("profile_image").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.profile_upgrade_to_pro)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.profile_create_account)).assertDoesNotExist()
    }

    @Test
    fun profileScreen_ProButtonIsVisible() {
        composeTestRule.setContent {
            ProfileScreen(
                state = mockStateWithPermanentUser,
                onLoginClick = { },
                onLogoutClick = { },
            )
        }

        val text = context.resources.getString(R.string.profile_upgrade_to_pro)

        composeTestRule.onNodeWithText(text = text).assertIsDisplayed()
    }

    @Test
    fun profileScreen_ProButtonClickTriggersAction() {
        var proClicked = false

        composeTestRule.setContent {
            ProfileScreen(
                state = ProfileUiState(),
                onLoginClick = { },
                onLogoutClick = { },
                onProClick = { proClicked = true }
            )
        }

        val text = context.resources.getString(R.string.profile_upgrade_to_pro)

        composeTestRule.onNodeWithText(text).performClick()
        assert(proClicked)
    }

    @Test
    fun profileScreen_LogoutProcessInitiated() {
        composeTestRule.setContent {
            ProfileScreen(
                state = ProfileUiState(),
                onLoginClick = { },
                onLogoutClick = { }
            )
        }

        val logOutText = context.resources.getString(R.string.logout)
        val logOutDialogMessage = context.resources.getString(R.string.confirm_logout_title)

        composeTestRule.onNodeWithText(logOutText).performClick()

        composeTestRule.onNodeWithText(logOutDialogMessage).assertIsDisplayed()
    }
}
