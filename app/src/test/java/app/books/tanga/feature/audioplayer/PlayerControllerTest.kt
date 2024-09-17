package app.books.tanga.feature.audioplayer

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import com.google.common.util.concurrent.ListenableFuture
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.concurrent.Executor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class PlayerControllerTest {

    private lateinit var playerControllerImpl: PlayerControllerImpl
    private lateinit var mockPlayer: MediaController
    private lateinit var mockControllerBuilder: MediaController.Builder
    private lateinit var mockControllerFuture: ListenableFuture<MediaController>

    @BeforeEach
    fun setup() {
        mockPlayer = mockk(relaxed = true)
        mockControllerBuilder = mockk()
        mockControllerFuture = mockk()

        every { mockControllerBuilder.buildAsync() } returns mockControllerFuture
        every { mockControllerFuture.get() } returns mockPlayer

        // Mock the addListener behavior
        every {
            mockControllerFuture.addListener(any(), any())
        } answers {
            val runnable = firstArg<Runnable>()
            val executor = secondArg<Executor>()
            executor.execute(runnable)
        }

        playerControllerImpl = PlayerControllerImpl(mockControllerBuilder)

        // Ensure player is initialized
        verifyPlayerInitialized()

        playerControllerImpl = PlayerControllerImpl(mockControllerBuilder)
    }

    private fun verifyPlayerInitialized() {
        verify(exactly = 1) {
            mockControllerFuture.addListener(any(), any())
        }
        verify(exactly = 1) {
            mockPlayer.addListener(playerControllerImpl)
        }
    }

    @Test
    fun `initPlayer should start periodic updates when player is already playing the same track`() =
        runTest {
            val track = Fixtures.audioTrack1
            every { mockPlayer.isPlaying } returns true
            every { mockPlayer.currentMediaItem } returns track.toMediaItem()
            every { mockPlayer.playbackState } returns Player.STATE_READY
            // We check player in a paused state to avoid starting the updates which will block the test
            every { mockPlayer.playWhenReady } returns false

            playerControllerImpl.initPlayer(track, this)

            // Advance time to trigger the first update
            advanceTimeBy(2000)
            playerControllerImpl.playbackState.test {
                val state = expectMostRecentItem()
                assertEquals(PlayerState.PAUSE, state.state)
            }
        }

    @Test
    fun `given player controller was initialized, then playAvailability should be available`() = runTest {
        playerControllerImpl.playAvailability.test {
            val state = expectMostRecentItem()
            assertEquals(PlayerAvailability.Available, state)
        }
    }

    @Test
    fun `given player controller was initialized, when getCurrentlyPlayingAudioTrack is called, then return the currently playing track`() =
        runTest {
            val track = Fixtures.audioTrack1

            every { mockPlayer.currentMediaItem } returns track.toMediaItem()

            playerControllerImpl.initPlayer(track, this)

            val result = playerControllerImpl.getCurrentlyPlayingAudioTrack()

            assertEquals(track.id, result?.id)
            assertEquals(track.title, result?.title)
            assertEquals(track.author, result?.author)
        }

    @Test
    fun `initPlayer should prepare new track when player is not playing`() = runTest {
        val track = Fixtures.audioTrack1
        every { mockPlayer.isPlaying } returns false
        every { mockPlayer.currentMediaItem } returns track.toMediaItem()

        playerControllerImpl.initPlayer(track, this)

        verify(exactly = 1) { mockPlayer.setMediaItem(any()) }
        verify(exactly = 1) { mockPlayer.prepare() }
    }

    @Test
    fun `initPlayer should not prepare new track when player is playing a different track`() =
        runTest {
            val track1 = Fixtures.audioTrack1
            val track2 = Fixtures.audioTrack2
            every { mockPlayer.isPlaying } returns true
            every { mockPlayer.currentMediaItem } returns track2.toMediaItem()

            playerControllerImpl.initPlayer(track1, this)

            verify(exactly = 0) { mockPlayer.setMediaItem(any()) }
            verify(exactly = 0) { mockPlayer.prepare() }
        }

    @Test
    fun `onPlayPause should handle new track correctly`() {
        val currentTrack = Fixtures.audioTrack1
        val newTrack = Fixtures.audioTrack2
        every { mockPlayer.currentMediaItem } returns currentTrack.toMediaItem()

        playerControllerImpl.onPlayPause(newTrack)

        verify { mockPlayer.stop() }
        verify { mockPlayer.setMediaItem(any()) }
        verify { mockPlayer.prepare() }
        verify { mockPlayer.playWhenReady = true }
    }

    @Test
    fun `onPlayPause should toggle playback for current track`() {
        val track = Fixtures.audioTrack1
        every { mockPlayer.currentMediaItem } returns track.toMediaItem()
        every { mockPlayer.playbackState } returns Player.STATE_READY
        every { mockPlayer.playWhenReady } returns false

        playerControllerImpl.onPlayPause(track)

        verify { mockPlayer.playWhenReady = true }
    }

    @Test
    fun `onForward should seek forward by SEEK_INTERVAL_MS`() {
        every { mockPlayer.currentPosition } returns 5000

        playerControllerImpl.onForward()

        verify { mockPlayer.seekTo(15000) }
    }

    @Test
    fun `onBackward should seek backward by SEEK_INTERVAL_MS`() {
        every { mockPlayer.currentPosition } returns 15000

        playerControllerImpl.onBackward()

        verify { mockPlayer.seekTo(5000) }
    }

    @Test
    fun `onSeekBarPositionChanged should seek to the specified position`() {
        playerControllerImpl.onSeekBarPositionChanged(20000)

        verify { mockPlayer.seekTo(20000) }
    }

    @Test
    fun `updatePlaybackStateBasedOnPlayer should update state correctly`() = runTest {
        every { mockPlayer.playWhenReady } returns true

        playerControllerImpl.onPlaybackStateChanged(Player.STATE_READY)

        val state = playerControllerImpl.playbackState.first()
        assertEquals(PlayerState.PLAYING, state.state)
    }

    @Test
    fun `releasePlayer should stop updates and release player`() {
        playerControllerImpl.releasePlayer()

        verify { mockPlayer.removeListener(any()) }
        verify { mockPlayer.release() }
    }
}
