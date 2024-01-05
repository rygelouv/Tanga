package app.books.tanga.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.feature.search.CategoriesSection
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.fixtures.FakeUiData
import junit.framework.TestCase.assertEquals
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test

class CategoriesSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun categoriesSection_displaysCategories() {
        val categories = FakeUiData.categories()

        composeTestRule.setContent {
            TangaTheme {
                CategoriesSection(categories = categories.toImmutableList())
            }
        }

        categories.forEach { category ->
            composeTestRule.onNodeWithText(category.name).assertExists()
        }
    }

    @Test
    fun categoriesSection_triggersOnCategorySelect() {
        var selectedCategory: CategoryUi? = null
        var unselectedCategory: CategoryUi? = null
        val categories = FakeUiData.categories()

        composeTestRule.setContent {
            TangaTheme {
                CategoriesSection(
                    categories = categories.toImmutableList(),
                    onCategorySelect = { selectedCategory = it },
                    onCategoryUnselect = { unselectedCategory = it }
                )
            }
        }

        val firstCategory = categories.first()
        composeTestRule.onNodeWithText(firstCategory.name).performClick()
        assertEquals(selectedCategory, firstCategory)
        composeTestRule.onNodeWithText(firstCategory.name).performClick()
        assertEquals(unselectedCategory, firstCategory)
    }

    @Test
    fun categoriesSection_triggersOnCategoryUnselectOnPreselectedCategories() {
        var didSelectedCategory = false
        var didUnselectedCategory = false
        var unselectCounter = 0
        val categories = FakeUiData.categories()

        composeTestRule.setContent {
            TangaTheme {
                CategoriesSection(
                    categories = categories.toImmutableList(),
                    selectedCategories = listOf(categories.first(), categories.last()).toImmutableList(),
                    onCategorySelect = { didSelectedCategory = true },
                    onCategoryUnselect = {
                        didUnselectedCategory = true
                        unselectCounter++
                    }
                )
            }
        }

        val firstCategory = categories.first()
        val lastCategory = categories.last()
        composeTestRule.onNodeWithText(firstCategory.name).performClick()
        composeTestRule.onNodeWithText(categories[categories.lastIndex - 1].name).performClick()
        composeTestRule.onNodeWithText(lastCategory.name).performClick()
        assertEquals(didSelectedCategory, true)
        assertEquals(didUnselectedCategory, true)
        assertEquals(unselectCounter, 2)
    }
}
