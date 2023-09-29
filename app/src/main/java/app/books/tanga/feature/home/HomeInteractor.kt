package app.books.tanga.feature.home

import app.books.tanga.data.category.CategoryRepository
import app.books.tanga.data.summary.SummaryRepository
import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.Section
import app.books.tanga.entity.Summary
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val userRepository: UserRepository,
    private val summaryRepository: SummaryRepository,
    private val categoryRepository: CategoryRepository
) {
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

    suspend fun getDailySummary(): Result<Summary> = TODO("Not yet implemented")

    suspend fun getUserInfo() = userRepository.getUser() // TODO think about error handling
}