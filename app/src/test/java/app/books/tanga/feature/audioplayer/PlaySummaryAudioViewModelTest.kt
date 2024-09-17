package app.books.tanga.feature.audioplayer

import app.books.tanga.data.download.DownloadUrlGenerator
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.audioplayer.fullplayer.PlaySummaryAudioViewModel
import app.books.tanga.feature.audioplayer.infrastructure.PlaybackState
import app.books.tanga.feature.audioplayer.infrastructure.PlayerController
import app.books.tanga.feature.audioplayer.infrastructure.PlayerState
import app.books.tanga.feature.summary.SummaryInteractor
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.books.tanga.tracking.AnalyticsTracker
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class PlaySummaryAudioViewModelTest {

    private lateinit var playerController: PlayerController
    private lateinit var summaryInteractor: SummaryInteractor
    private lateinit var viewModel: PlaySummaryAudioViewModel
    private lateinit var downloadUrlGenerator: DownloadUrlGenerator
    private val analyticsTracker: AnalyticsTracker = mockk(relaxUnitFun = true)
    private val playbackStateFlow = MutableStateFlow(PlaybackState())

    @BeforeEach
    fun setUp() {
        playerController = mockk(relaxed = true)
        summaryInteractor = mockk(relaxed = true)
        downloadUrlGenerator = mockk(relaxed = true)
        coEvery { playerController.playbackState } returns playbackStateFlow
        coEvery { downloadUrlGenerator.generateAudioDownloadUrl(any()) } returns "http://example.com/audio.mp3"
    }

    @Test
    fun `PlayerController playback state changes updates viewModel state`() = runTest {
        viewModel =
            PlaySummaryAudioViewModel(playerController, summaryInteractor, downloadUrlGenerator, analyticsTracker)

        viewModel.state.test {
            val item = awaitItem()
            assertEquals(PlaybackState(), item.playbackState)

            launch {
                playbackStateFlow.emit(PlaybackState(state = PlayerState.PLAYING))
            }
            val item2 = awaitItem()
            assertEquals(PlayerState.PLAYING, item2.playbackState?.state)
        }
    }

    @Test
    fun `Load summary successfully updates viewModel state and initializes player`() = runTest {
        val summaryId = SummaryId("1")
        val summary = Fixtures.dummySummary1
        val audioTrack = Fixtures.audioTrack1

        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.success(summary)
        coEvery { playerController.initPlayer(audioTrack, any()) } just Runs
        coEvery { downloadUrlGenerator.generateAudioDownloadUrl(summaryId) } returns "http://example.com/audio.mp3"

        viewModel =
            PlaySummaryAudioViewModel(playerController, summaryInteractor, downloadUrlGenerator, analyticsTracker)

        viewModel.state.test {
            viewModel.loadSummary(summaryId)

            val item = expectMostRecentItem()
            assertEquals(summaryId, SummaryId(item.summaryId!!))
            assertEquals("Content1", item.title)
            assertEquals("Author1", item.author)
            assertEquals("12:20", item.duration)
            assertEquals("http://example.com/image.png", item.coverUrl)

            coVerify { playerController.initPlayer(audioTrack, any()) }
        }
    }

    @Test
    fun `Loading summary failure updates viewModel state with error`() = runTest {
        val summaryId = SummaryId("summary123")
        val exception = RuntimeException("Error")

        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.failure(exception)

        viewModel =
            PlaySummaryAudioViewModel(playerController, summaryInteractor, downloadUrlGenerator, analyticsTracker)

        viewModel.state.test {
            viewModel.loadSummary(summaryId)

            val item = expectMostRecentItem()
            assertNotNull(item.error)
        }
    }
}
