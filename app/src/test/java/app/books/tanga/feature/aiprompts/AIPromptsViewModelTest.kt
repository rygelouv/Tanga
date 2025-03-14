package app.books.tanga.feature.aiprompts

import app.books.tanga.data.aiprompts.AIPromptsRepository
import app.books.tanga.entity.AIPrompt
import app.books.tanga.entity.SummaryId
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class AIPromptsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private val repository: AIPromptsRepository = mockk()
    private lateinit var viewModel: AIPromptsViewModel

    @BeforeEach
    fun setUp() {
        viewModel = AIPromptsViewModel(repository)
    }

    @Test
    fun `should fetch AI prompts successfully`() = scope.runTest {
        // Given
        val summaryId = SummaryId("designing_your_life")
        val prompts = listOf(
            AIPrompt(id = "1", title = "Prompt 1", description = "Description 1"),
            AIPrompt(id = "2", title = "Prompt 2", description = "Description 2")
        )
        coEvery { repository.getAIPromptsForSummary(summaryId) } returns Result.success(prompts)

        // When
        viewModel.getAIPromptsForSummary(summaryId)

        // Then
        viewModel.state.test {
            val promptsResult = awaitItem().prompts
            assertEquals(promptsResult, prompts.map { it.toUi() })
        }

        coVerify(exactly = 1) { repository.getAIPromptsForSummary(summaryId) }
    }

    @Test
    fun `should handle failure when fetching AI prompts`() = scope.runTest {
        // Given
        val summaryId = SummaryId("designing_your_life")
        val exception = RuntimeException("Network error")
        coEvery { repository.getAIPromptsForSummary(summaryId) } returns Result.failure(exception)

        // When
        viewModel.getAIPromptsForSummary(summaryId)

        // Then
        viewModel.state.test {
            val promptsResult = awaitItem().prompts
            assertEquals(promptsResult, null)
        }

        coVerify(exactly = 1) { repository.getAIPromptsForSummary(summaryId) }
    }
}
