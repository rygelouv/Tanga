package app.books.tanga.feature.audioplayer

import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.audioplayer.infrastructure.AudioTrack
import app.books.tanga.feature.audioplayer.infrastructure.PlaybackState
import app.books.tanga.feature.audioplayer.infrastructure.PlayerAvailability
import app.books.tanga.feature.audioplayer.infrastructure.PlayerController
import app.books.tanga.feature.audioplayer.infrastructure.PlayerState
import app.books.tanga.feature.audioplayer.miniplayer.MiniPlayerViewModel
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class MiniPlayerViewModelTest {

    private lateinit var viewModel: MiniPlayerViewModel
    private val playerController: PlayerController = mockk(relaxed = true)
    private val playbackStateFlow = MutableStateFlow(PlaybackState())
    private val playAvailabilityFlow: MutableStateFlow<PlayerAvailability> =
        MutableStateFlow(PlayerAvailability.Unavailable)

    @BeforeEach
    fun setUp() {
        every { playerController.playbackState } returns playbackStateFlow
        every { playerController.playAvailability } returns playAvailabilityFlow
        viewModel = MiniPlayerViewModel(playerController)
    }

    @Test
    fun `given playback state when init is called then updates UI`() = runTest {
        viewModel.init()
        playbackStateFlow.value = PlaybackState(state = PlayerState.PLAYING)
        assert(viewModel.state.value.playerState == PlayerState.PLAYING)
    }

    @Test
    fun `given play availability when init is called then loads currently playing summary`() = runTest {
        val audioTrack = mockk<AudioTrack> {
            every { id } returns "1"
            every { title } returns "Title"
            every { author } returns "Author"
            every { coverUrl } returns "CoverUrl"
        }
        every { playerController.getCurrentlyPlayingAudioTrack() } returns audioTrack

        viewModel.init()
        playAvailabilityFlow.value = PlayerAvailability.Available

        assert(viewModel.state.value.showMiniPlayer)
        assert(viewModel.state.value.summaryId == SummaryId("1"))
        assert(viewModel.state.value.title == "Title")
        assert(viewModel.state.value.author == "Author")
        assert(viewModel.state.value.coverUrl == "CoverUrl")
    }

    @Test
    fun `given mini player when dismissed then hides mini player`() {
        viewModel.onDismissMiniPlayer()
        assert(!viewModel.state.value.showMiniPlayer)
    }
}
