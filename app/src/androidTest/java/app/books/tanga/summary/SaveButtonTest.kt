package app.books.tanga.summary

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.summary.details.SaveButton
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class SaveButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSaveButtonIconDisplay() {
        composeTestRule.setContent {
            SaveButton(
                progressState = ProgressState.Hide,
                isSaved = false,
                onClick = {}
            )
        }

        // Assert that the "saved summary" icon is displayed
        composeTestRule.onNodeWithTag("save_favorite_icon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("remove_favorite_icon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun testRemoveButtonIconDisplay() {
        composeTestRule.setContent {
            SaveButton(
                progressState = ProgressState.Hide,
                isSaved = true,
                onClick = {}
            )
        }

        // Assert that the "remove summary" icon is displayed
        composeTestRule.onNodeWithTag("remove_favorite_icon", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("save_favorite_icon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun testSaveButtonLoadingState() {
        composeTestRule.setContent {
            SaveButton(
                progressState = ProgressState.Show,
                onClick = {}
            )
        }

        composeTestRule.onNodeWithTag("saving_favorite_progress").assertIsDisplayed()
    }

    @Test
    fun testSaveButtonClickInteraction() {
        val onClickMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            SaveButton(
                progressState = ProgressState.Hide,
                onClick = onClickMock
            )
        }

        composeTestRule.onNodeWithTag("save_favorite").performClick()

        verify { onClickMock.invoke() }
    }

    @Test
    fun testClickOnProgressIndicatorDoesNotTriggerCallback() {
        val onClickMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            SaveButton(
                progressState = ProgressState.Show,
                onClick = onClickMock
            )
        }

        composeTestRule.onNodeWithTag("save_favorite").performClick()

        verify(exactly = 0) { onClickMock.invoke() }
    }
}
