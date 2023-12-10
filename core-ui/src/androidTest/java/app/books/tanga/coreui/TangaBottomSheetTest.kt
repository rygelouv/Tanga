package app.books.tanga.coreui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import app.books.tanga.coreui.components.ActionContent
import app.books.tanga.coreui.components.ActionData
import app.books.tanga.coreui.components.BottomSheetData
import app.books.tanga.coreui.components.Button
import app.books.tanga.coreui.components.TangaBottomSheet
import app.books.tanga.coreui.components.TangaBottomSheetContainer
import app.books.tanga.coreui.resources.TextResource
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class TangaBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomSheetRendersCorrectly() {
        var dismissed = false
        val actionData = ActionData(
            title = TextResource.fromText("Expected Title"),
            description = TextResource.fromText("Expected Description"),
            icon = R.drawable.graphic_book_search,
            mainButton = Button(
                text = TextResource.fromText("Main Button Text"),
                onClick = { dismissed = true }
            )
        )

        val bottomSheetData = BottomSheetData(
            actionData = actionData,
            onDismiss = { dismissed = true }
        )

        composeTestRule.setContent {
            TangaBottomSheet(
                data = bottomSheetData.copy(onDismiss = bottomSheetData.onDismiss),
                modifier = Modifier.fillMaxSize()
            )
        }

        // Attempt to expand the bottom sheet

        composeTestRule.onNodeWithText("Main Button Text").assertExists()
        composeTestRule.onNodeWithText("Main Button Text").performClick()

        composeTestRule.onNodeWithTag("Main Button Text").assertDoesNotExist()

        assertTrue(dismissed)
    }

    @Test
    fun testBottomSheetContainerRendersContentCorrectly() {
        var dismissed = false
        val actionData = ActionData(
            title = TextResource.fromText("Expected Title"),
            description = TextResource.fromText("Expected Description"),
            icon = R.drawable.graphic_book_search,
            mainButton = Button(
                text = TextResource.fromText("Main Button Text"),
                onClick = { dismissed = true }
            ),
            secondaryButton = Button(
                text = TextResource.fromText("Secondary Button Text"),
                onClick = {}
            )
        )

        val bottomSheetData = BottomSheetData(
            actionData = actionData,
            onDismiss = { dismissed = true }
        )

        composeTestRule.setContent {
            TangaBottomSheetContainer(
                content = {
                    ActionContent(data = bottomSheetData.actionData)
                },
                onDismiss = { dismissed = true }
            )
        }

        // Attempt to expand the bottom sheet

        composeTestRule.onNodeWithText("Main Button Text").assertExists()
        composeTestRule.onNodeWithText("Main Button Text").performClick()

        composeTestRule.onNodeWithText("Secondary Button Text").assertExists()

        composeTestRule.onNodeWithTag("Main Button Text").assertDoesNotExist()

        assertTrue(dismissed)
    }
}
