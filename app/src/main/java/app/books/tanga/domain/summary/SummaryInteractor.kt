package app.books.tanga.domain.summary

import app.books.tanga.data.CategoryRepository
import app.books.tanga.data.SummaryRepository
import app.books.tanga.domain.categories.Category
import app.books.tanga.domain.categories.Section
import javax.inject.Inject

class SummaryInteractor @Inject constructor(
    private val summaryRepository: SummaryRepository,
    private val categoryRepository: CategoryRepository
) {
    suspend fun getSummary(summaryId: String): Result<Summary> {
        return summaryRepository.getSummary(summaryId)
    }

    /**
     * Get the list of summaries grouped by category
     */
    suspend fun getSummarySections(): Result<List<Section>> {
        return runCatching {
            val categories = categoryRepository.getCategories()
            val summaries = summaryRepository.getAllSummaries().getOrNull()

            val sections = mutableListOf<Section>()

            categories.onSuccess {
                it.forEach { category ->
                    val summariesByCategory = summaries?.filter { summary ->
                        summary.categories.contains(category.id)
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

    /**
     * Get the list of summaries for the given category ids
     * If the list of category ids is empty, return all summaries
     */
    suspend fun getSummariesForCategories(categoryIds: List<String>): Result<List<Summary>> {
        return runCatching {
            if (categoryIds.isEmpty()) return@runCatching getAllSummaries().getOrThrow()

            val summaries = mutableListOf<Summary>()

            categoryIds.forEach { categoryId ->
                val summariesByCategory = getSummariesByCategory(categoryId)
                summariesByCategory.onSuccess {
                    summaries.addAll(it)
                }
            }

            // Remove duplicates
            summaries.toSet().toList()
        }
    }

    suspend fun search(query: String): Result<List<Summary>> {
        return summaryRepository.searchSummaryInMemoryCache(query)
    }

    private suspend fun getSummariesByCategory(categoryId: String): Result<List<Summary>> {
        return summaryRepository.getSummariesByCategory(categoryId)
    }

    /**
     * Get a list of recommended summaries for the given summary
     * The recommendations are based on the categories of the summary
     */
    suspend fun getRecommendationsForSummary(summary: Summary): Result<List<Summary>> {
        return runCatching {
            val recommendedSummaries = mutableListOf<Summary>()
            summary.categories.forEach {
                val summaries = getSummariesByCategory(it).getOrThrow()
                recommendedSummaries.addAll(summaries)
            }
            recommendedSummaries.shuffled().take(4)
        }
    }

    /**
     * Get all summaries
     * Once the summaries are retrieved from the repository, save them in the in memory cache
     */
    suspend fun getAllSummaries(): Result<List<Summary>> {
        val summaries = summaryRepository.getAllSummaries()
        summaryRepository.saveSummariesInMemoryCache(summaries.getOrNull() ?: emptyList())
        return summaries
    }

    suspend fun getCategories(): Result<List<Category>> {
        return categoryRepository.getCategories()
    }
}