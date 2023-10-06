package app.books.tanga.feature.summary

import app.books.tanga.data.category.CategoryRepository
import app.books.tanga.data.summary.SummaryRepository
import app.books.tanga.entity.Category
import app.books.tanga.entity.Summary
import app.books.tanga.utils.resultOf
import javax.inject.Inject

private const val RECOMMENDED_SUMMARIES_COUNT = 5

class SummaryInteractor @Inject constructor(
    private val summaryRepository: SummaryRepository,
    private val categoryRepository: CategoryRepository
) {
    suspend fun getSummary(summaryId: String): Result<Summary> {
        return summaryRepository.getSummary(summaryId)
    }

    /**
     * Get the list of summaries for the given category ids
     * If the list of category ids is empty, return all summaries
     */
    suspend fun getSummariesForCategories(categoryIds: List<String>): Result<List<Summary>> {
        return resultOf {
            if (categoryIds.isEmpty()) return Result.success(getAllSummaries().getOrThrow())

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
        return resultOf {
            val recommendedSummaries = mutableListOf<Summary>()
            summary.categories.forEach {
                val summaries = getSummariesByCategory(it.value).getOrThrow()
                recommendedSummaries.addAll(summaries)
            }
            // Remove the summary itself from the list and
            // Remove duplicates and shuffle the list then take only a subset of the list
            recommendedSummaries
                .removeSummary(summary)
                .toSet()
                .toList()
                .shuffled()
                .take(RECOMMENDED_SUMMARIES_COUNT)
            return Result.success(recommendedSummaries)
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

    private fun MutableList<Summary>.removeSummary(summary: Summary): MutableList<Summary> {
        this.remove(summary)
        return this
    }
}