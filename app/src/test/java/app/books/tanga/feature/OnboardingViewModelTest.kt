package app.books.tanga.feature

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.feature.onboarding.OnboardingViewModel
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class OnboardingViewModelTest {

    private lateinit var repository: DefaultPrefDataStoreRepository
    private lateinit var viewModel: OnboardingViewModel

    @BeforeEach
    fun setup() {
        // Create a mock of DefaultPrefDataStoreRepository
        repository = mockk(relaxed = true)

        // Initialize the OnboardingViewModel with the mock repository
        viewModel = OnboardingViewModel(repository)
    }

    @Test
    fun `onOnboardingCompleted should save completion state to repository`() = runTest {
        // Call the onOnboardingCompleted function which should trigger the repository call
        viewModel.onOnboardingCompleted()

        // Verify that the repository's saveOnboardingCompletionState function was called with true
        coVerify { repository.saveOnboardingCompletionState(true) }
    }
}
