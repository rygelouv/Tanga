package app.books.tanga.coreui

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import app.books.tanga.coreui.components.InfoCard
import app.books.tanga.coreui.theme.TangaTheme
import org.junit.Rule
import org.junit.Test

class InfoCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun infoCard_displaysImage() {
        val imageResId = R.drawable.graphic_questions_simple

        composeTestRule.setContent {
            TangaTheme {
                InfoCard(image = imageResId)
            }
        }

        composeTestRule.onNodeWithTag("info_card_text").assertExists()
        composeTestRule.onNodeWithTag("info_card_text").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Info Card Image").assertExists()
        composeTestRule.onNodeWithContentDescription("Info Card Image").assertIsDisplayed()
        composeTestRule.onNodeWithTag("info_card").assertHasNoClickAction()
    }
}
