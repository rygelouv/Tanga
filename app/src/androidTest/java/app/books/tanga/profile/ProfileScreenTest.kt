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
import app.books.tanga.feature.profile.HandleEvents
import app.books.tanga.feature.profile.ProfileScreen
import app.books.tanga.feature.profile.ProfileScreenContainer
import app.books.tanga.feature.profile.ProfileUiEvent
import app.books.tanga.feature.profile.ProfileUiState
import app.books.tanga.feature.profile.ProfileViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

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

    @Ignore("Flaky on smaller devices")
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

    @Ignore("Flaky on smaller devices")
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

    @Test
    fun profileScreenContainer_RendersCorrectlyWhenUserIsLoggedIn() {
        val viewModel = mockk<ProfileViewModel>()
        val mockState = mockk<StateFlow<ProfileUiState>>(relaxed = true)
        val mockEvent = mockk<Flow<ProfileUiEvent>>(relaxed = true)

        every { mockState.value } returns mockStateWithPermanentUser
        every { viewModel.state } returns mockState
        every { viewModel.events } returns mockEvent

        composeTestRule.setContent {
            ProfileScreenContainer(
                onNavigateToAuth = {},
                onNavigateToPricing = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithTag("profile_image").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.profile_upgrade_to_pro)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.profile_create_account)).assertDoesNotExist()
    }
}

class HandleEventsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockOnNavigateToAuth: () -> Unit = mock()
    private val mockOnNavigateToPricing: () -> Unit = mock()

    @Test
    fun navigateToAuthEvent_callsOnNavigateToAuth() {
        composeTestRule.setContent {
            HandleEvents(
                event = ProfileUiEvent.NavigateTo.ToAuth(false),
                onNavigateToAuth = mockOnNavigateToAuth,
                onNavigateToPricing = {}
            )
        }

        verify(mockOnNavigateToAuth).invoke()
    }

    @Test
    fun navigateToPricingPlanEvent_callsOnNavigateToPricing() {
        composeTestRule.setContent {
            HandleEvents(
                event = ProfileUiEvent.NavigateTo.ToPricingPlan(false),
                onNavigateToAuth = {},
                onNavigateToPricing = mockOnNavigateToPricing
            )
        }

        verify(mockOnNavigateToPricing).invoke()
    }
}
