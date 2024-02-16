package app.books.tanga.feature.read

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.data.download.FileDownloader
import app.books.tanga.feature.summary.SummaryBehaviorDelegate
import app.books.tanga.feature.summary.SummaryContentState
import app.books.tanga.feature.summary.toSummaryUi
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
@ExperimentalTime
class ReadSummaryViewModelTest {

    private lateinit var viewModel: ReadSummaryViewModel
    private lateinit var summaryBehaviorDelegate: SummaryBehaviorDelegate
    private lateinit var fileDownloader: FileDownloader

    @BeforeEach
    fun setUp() {
        summaryBehaviorDelegate = mockk<SummaryBehaviorDelegate>()
        fileDownloader = mockk<FileDownloader>()

        coEvery { summaryBehaviorDelegate.setUp(any()) } just Runs
        coEvery { summaryBehaviorDelegate.loadSummary(any()) } just Runs
    }

    @Test
    fun `loadSummary updates state correctly on success`() = runTest {
        val summaryId = Fixtures.dummySummary1.id
        val mockTextBytes = "summary content".toByteArray()
        val expectedTextContent = mockTextBytes.toString(Charsets.UTF_8)

        // Mocking the file downloader behavior
        coEvery { fileDownloader.downloadSummaryText(any()) } returns Result.success(mockTextBytes)
        coEvery { summaryBehaviorDelegate.summaryContentState } returns flowOf(
            SummaryContentState(
                summary = Fixtures.dummySummary1.toSummaryUi(),
                error = null
            )
        ).stateIn(this)

        viewModel = ReadSummaryViewModel(summaryBehaviorDelegate, fileDownloader)

        viewModel.state.test {
            viewModel.loadSummary(summaryId)

            // Skip the initial state emission
            awaitItem()

            // Assert that the progress state is shown first
            assertEquals(ProgressState.Show, awaitItem().progressState)

            // Then it should hide the progress and update the text content
            with(expectMostRecentItem()) {
                assertEquals(ProgressState.Hide, progressState)
                assertEquals(expectedTextContent, summaryTextContent)
            }
        }

        // Verify the summaryBehaviorDelegate.loadSummary was called
        verify { summaryBehaviorDelegate.loadSummary(summaryId) }
    }

    @Test
    fun `loadSummary updates state correctly on failure`() = runTest {
        val summaryId = Fixtures.dummySummary1.id
        val error = RuntimeException("Failed to download")

        // Mocking the file downloader behavior to return a failure
        coEvery { fileDownloader.downloadSummaryText(any()) } returns Result.failure(error)
        coEvery { summaryBehaviorDelegate.summaryContentState } returns flowOf(
            SummaryContentState(
                summary = Fixtures.dummySummary1.toSummaryUi(),
                error = null
            )
        ).stateIn(this)

        viewModel = ReadSummaryViewModel(summaryBehaviorDelegate, fileDownloader)

        viewModel.state.test {
            viewModel.loadSummary(summaryId)

            // Skip the initial state emission and the show progress state
            awaitItem()
            awaitItem()

            // Assert that the progress state is hidden and error is set
            with(awaitItem()) {
                assertEquals(ProgressState.Hide, progressState)
                assertNotNull(error)
            }
        }
    }

    @Test
    fun `onPlayAudioClicked posts correct event`() = runTest {
        // Assuming a valid summaryId is set in the state
        val summaryId = Fixtures.dummySummary1.id

        coEvery { summaryBehaviorDelegate.summaryContentState } returns flowOf(
            SummaryContentState(
                summary = Fixtures.dummySummary1.toSummaryUi(),
                error = null
            )
        ).stateIn(this)

        viewModel = ReadSummaryViewModel(summaryBehaviorDelegate, fileDownloader)

        viewModel.onPlayAudioClicked()

        viewModel.events.test {
            val event = awaitItem() as? ReadSummaryUiEvent.NavigateTo.ToAudioPlayer
            assertNotNull(event)
            assertEquals(summaryId, event?.summaryId)
        }
    }

    @Test
    fun `onFontSizeClicked toggles fontSizeChooserVisible`() = runTest {
        coEvery { summaryBehaviorDelegate.summaryContentState } returns flowOf(
            SummaryContentState(
                summary = Fixtures.dummySummary1.toSummaryUi(),
                error = null
            )
        ).stateIn(this)

        viewModel = ReadSummaryViewModel(summaryBehaviorDelegate, fileDownloader)

        val initialFontSizeChooserVisible = viewModel.state.value.fontSizeChooserVisible

        viewModel.onFontSizeClicked()

        val newFontSizeChooserVisible = viewModel.state.value.fontSizeChooserVisible
        assertNotEquals(initialFontSizeChooserVisible, newFontSizeChooserVisible)
    }

    @Test
    fun `onScaleChanged updates textScaleFactor`() = runTest {
        val scale = 0.5f

        coEvery { summaryBehaviorDelegate.summaryContentState } returns flowOf(
            SummaryContentState(
                summary = Fixtures.dummySummary1.toSummaryUi(),
                error = null
            )
        ).stateIn(this)

        viewModel = ReadSummaryViewModel(summaryBehaviorDelegate, fileDownloader)

        viewModel.onScaleChanged(scale)

        val newTextScaleFactor = viewModel.state.value.textScaleFactor
        assertEquals(scale.toScaleFactor(), newTextScaleFactor)
    }
}
