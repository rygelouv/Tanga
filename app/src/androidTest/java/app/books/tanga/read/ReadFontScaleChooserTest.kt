package app.books.tanga.read

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import app.books.tanga.feature.read.components.ReadFontScaleChooser
import org.junit.Rule
import org.junit.Test

class ReadFontScaleChooserTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun readFontScaleChooser_renders_correctly() {
        var scale = 0f
        composeTestRule.setContent {
            ReadFontScaleChooser(
                initialValue = scale,
                onFontScaleChange = { scale = it }
            )
        }

        composeTestRule.onNodeWithTag("font_size_min").assertExists()
        composeTestRule.onNodeWithTag("font_size_max").assertExists()
    }

    @Test
    fun readFontScaleChooser_updates_scale_on_slider_interaction() {
        var scale = 0f
        composeTestRule.setContent {
            ReadFontScaleChooser(
                initialValue = scale,
                onFontScaleChange = { scale = it }
            )
        }

        composeTestRule.onNodeWithTag("slider").performTouchInput { swipeRight() }

        assert(scale > 0f)
    }
}
