package app.books.tanga.feature.home

import app.books.tanga.data.CategoryRepository
import app.books.tanga.data.SummaryRepository
import app.books.tanga.domain.categories.Section
import javax.inject.Inject

class SummaryInteractor @Inject constructor(
    private val summaryRepository: SummaryRepository,
    private val categoryRepository: CategoryRepository
) {

    /**
     * Get the list of summaries grouped by category
     */
    suspend fun getSummarySections(): Result<List<Section>> {
        return runCatching {
            val categories = categoryRepository.getCategories()
            val summaries = summaryRepository.getSummaries().getOrNull()

            val sections = mutableListOf<Section>()

            categories.onSuccess {
                it.forEach { category ->
                    val summariesByCategory = summaries?.filter { summary ->
                        summary.categories.contains(category.slug)
                    } ?: emptyList()

                    sections.add(
                        Section(
                            category = category,
                            summaries = summariesByCategory
                        )
                    )
                }
            }

            sections
        }
    }
}