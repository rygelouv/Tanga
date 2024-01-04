package app.books.tanga.feature.home

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.Section
import app.books.tanga.errors.UiError
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.feature.summary.toSummaryUi

data class HomeUiState(
    val progressState: ProgressState = ProgressState.Hide,
    val userFirstName: String? = null,
    val userPhotoUrl: String? = null,
    val weeklySummary: SummaryUi? = null,
    val sections: List<HomeSectionUi>? = null,
    val error: UiError? = null
)

/**
 * Class representing a section in the home screen.
 * title: the title of the section which is the category name
 * summaries: the list of summaries in the section and that belong to the category
 */
data class HomeSectionUi(
    val categoryId: CategoryId,
    val title: String,
    val summaries: List<SummaryUi>
)

fun Section.toHomeSectionUi(): HomeSectionUi =
    HomeSectionUi(
        categoryId = category.id,
        title = category.name,
        summaries = summaries.map { it.toSummaryUi() }
    )
