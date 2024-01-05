package app.books.tanga.feature.home

import app.books.tanga.data.category.CategoryRepository
import app.books.tanga.data.summary.SummaryRepository
import app.books.tanga.entity.Section
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.entity.User
import app.books.tanga.feature.profile.ProfileInteractor
import javax.inject.Inject

private const val SECTION_SUMMARIES_COUNT = 5

class HomeInteractor @Inject constructor(
    private val summaryRepository: SummaryRepository,
    private val categoryRepository: CategoryRepository,
    private val profileInteractor: ProfileInteractor
) {
    /**
     * Get the list of summaries grouped by category
     */
    suspend fun getSummarySections(): Result<List<Section>> {
        val categories = categoryRepository.getCategories()

        val sections = mutableListOf<Section>()

        categories.map {
            it.forEach { category ->
                val summariesByCategory = summaryRepository.getSummariesByCategory(category.id.value).getOrNull()
                summariesByCategory?.let { summaries ->
                    sections.add(
                        Section(
                            category = category,
                            summaries = summaries.take(SECTION_SUMMARIES_COUNT)
                        )
                    )
                }
            }
        }.onFailure {
            return Result.failure(it)
        }

        return Result.success(sections)
    }

    // TODO to be implemented
    suspend fun getWeeklySummary(): Result<Summary> = summaryRepository.getSummary(SummaryId("atomic_habits"))

    suspend fun getUserInfo(): Result<User?> = profileInteractor.getUserInfo()
}
