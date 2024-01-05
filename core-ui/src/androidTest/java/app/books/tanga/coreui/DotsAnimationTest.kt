package app.books.tanga.coreui

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import app.books.tanga.coreui.components.DotsAnimation
import org.junit.Rule
import org.junit.Test

class DotsAnimationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dotsAnimation_displays_correct_number_of_dots() {
        val numberOfDots = 5
        composeTestRule.setContent {
            DotsAnimation(numberOfDots = numberOfDots)
        }

        composeTestRule.onAllNodesWithTag("Dot").assertCountEquals(numberOfDots)
    }

    @Test
    fun dotsAnimation_displays_no_dots_when_number_is_zero() {
        composeTestRule.setContent {
            DotsAnimation(numberOfDots = 0)
        }

        composeTestRule.onNodeWithTag("Dot").assertDoesNotExist()
    }
}
