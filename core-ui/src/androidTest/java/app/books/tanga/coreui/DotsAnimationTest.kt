package app.books.tanga.coreui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.sendwave.remit.feature.support.chat.DotsAnimation
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

        repeat(numberOfDots) {
            composeTestRule.onNodeWithText("Dot").assertExists()
        }
    }

    @Test
    fun dotsAnimation_displays_no_dots_when_number_is_zero() {
        composeTestRule.setContent {
            DotsAnimation(numberOfDots = 0)
        }

        composeTestRule.onNodeWithText("Dot").assertDoesNotExist()
    }

    @Test
    fun dotsAnimation_displays_one_dot_when_number_is_negative() {
        composeTestRule.setContent {
            DotsAnimation(numberOfDots = -1)
        }

        composeTestRule.onNodeWithText("Dot").assertExists()
    }
}
