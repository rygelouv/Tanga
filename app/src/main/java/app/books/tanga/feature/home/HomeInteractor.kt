package app.books.tanga.feature.home

import app.books.tanga.data.category.CategoryRepository
import app.books.tanga.data.summary.SummaryRepository
import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.Section
import app.books.tanga.entity.Summary
import app.books.tanga.entity.User
import app.books.tanga.errors.DomainError
import app.books.tanga.feature.profile.ProfileInteractor
import javax.inject.Inject

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
        val summaries = summaryRepository.getAllSummaries().getOrNull()

        val sections = mutableListOf<Section>()

        categories.map {
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
        }.onFailure {
            return Result.failure(it)
        }

        return Result.success(sections)
    }

    suspend fun getDailySummary(): Result<Summary> = TODO("Not yet implemented")

    suspend fun getUserInfo(): Result<User?> = profileInteractor.getUserInfo()
}