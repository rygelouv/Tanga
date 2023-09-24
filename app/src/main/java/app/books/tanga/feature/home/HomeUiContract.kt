package app.books.tanga.feature.home

import app.books.tanga.entity.Section
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.feature.summary.toSummaryUi

data class HomeUiState(
    val userFirstName: String? = null,
    val userPhotoUrl: String? = null,
    val sections: List<HomeSectionUi>? = null,
    val error: Throwable? = null
)

/**
 * Class representing a section in the home screen.
 * title: the title of the section which is the category name
 * summaries: the list of summaries in the section and that belong to the category
 */
data class HomeSectionUi(
    val title: String,
    val summaries: List<SummaryUi>
)

fun Section.toHomeSectionUi(): HomeSectionUi {
    return HomeSectionUi(
        title = category.name,
        summaries = summaries.map { it.toSummaryUi() }
    )
}