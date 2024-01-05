package app.books.tanga.feature.home

import app.books.tanga.data.category.CategoryRepository
import app.books.tanga.data.summary.SummaryRepository
import app.books.tanga.entity.Section
import app.books.tanga.feature.profile.ProfileInteractor
import app.books.tanga.fixtures.Fixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HomeInteractorTest {

    private lateinit var summaryRepository: SummaryRepository
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var profileInteractor: ProfileInteractor
    private lateinit var homeInteractor: HomeInteractor

    @BeforeEach
    fun setup() {
        summaryRepository = mockk()
        categoryRepository = mockk()
        profileInteractor = mockk()
        homeInteractor = HomeInteractor(summaryRepository, categoryRepository, profileInteractor)
    }

    @Test
    fun `HomeInteractor retrieves summary sections successfully`() = runTest {
        val expectedSections = listOf(Fixtures.dummySection1, Fixtures.dummySection2)
        coEvery {
            categoryRepository.getCategories()
        } returns Result.success(listOf(Fixtures.dummyCategory1, Fixtures.dummyCategory2))
        coEvery { summaryRepository.getSummariesByCategory("1") } returns Result.success(Fixtures.summariesForCategory1)
        coEvery { summaryRepository.getSummariesByCategory("2") } returns Result.success(Fixtures.summariesForCategory2)

        val actualSections = homeInteractor.getSummarySections()

        assertEquals(Result.success(expectedSections), actualSections)
        coVerify { categoryRepository.getCategories() }
        coVerify(exactly = 2) { summaryRepository.getSummariesByCategory(any()) }
    }

    @Test
    fun `HomeInteractor handles no categories scenario`() = runTest {
        coEvery { categoryRepository.getCategories() } returns Result.success(emptyList())

        val actualSections = homeInteractor.getSummarySections()

        assertEquals(Result.success(emptyList<Section>()), actualSections)
        coVerify { categoryRepository.getCategories() }
    }

    @Test
    fun `HomeInteractor retrieves daily summary successfully`() = runTest {
        val expectedSummary = Result.success(Fixtures.dummySummary1)
        coEvery { homeInteractor.getWeeklySummary() } returns expectedSummary

        val actualSummary = homeInteractor.getWeeklySummary()

        assertEquals(expectedSummary, actualSummary)
    }

    @Test
    fun `HomeInteractor retrieves user info successfully`() = runTest {
        val expectedUser = Result.success(Fixtures.dummyUser)
        coEvery { profileInteractor.getUserInfo() } returns expectedUser

        val actualUser = homeInteractor.getUserInfo()

        assertEquals(expectedUser, actualUser)
    }
}
