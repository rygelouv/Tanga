package app.books.tanga.feature.audioplayer

import androidx.media3.common.Player
import androidx.media3.session.MediaController
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import com.google.common.util.concurrent.ListenableFuture
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
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
class PlayerControllerImplTest {

    private lateinit var playerControllerImpl: PlayerControllerImpl
    private lateinit var mockPlayer: Player
    private lateinit var mockControllerBuilder: MediaController.Builder
    private lateinit var mockControllerFuture: ListenableFuture<MediaController>

    @BeforeEach
    fun setup() {
        mockPlayer = mockk(relaxed = true)
        mockControllerBuilder = mockk()
        mockControllerFuture = mockk()

        every { mockControllerBuilder.buildAsync() } returns mockControllerFuture
        every { mockControllerFuture.addListener(any(), any()) } just Runs
        every { mockControllerFuture.get() } returns mockPlayer as MediaController

        playerControllerImpl = PlayerControllerImpl(mockControllerBuilder)
    }

    @Test
    fun `initPlayer should start periodic updates when player is already playing the same track`() = runTest {
        val track = Fixtures.audioTrack1
        every { mockPlayer.isPlaying } returns true
        every { mockPlayer.currentMediaItem?.mediaId } returns "1"
        every { mockPlayer.playbackState } returns Player.STATE_READY

        playerControllerImpl.initPlayer(track, this)

        advanceTimeBy(2000)
        val state = playerControllerImpl.playbackState.first()
        assertEquals(PlayerState.PLAYING, state.state)
    }

    @Test
    fun `initPlayer should not prepare new track when player is playing a different track`() = runTest {
        val track = Fixtures.audioTrack1
        every { mockPlayer.isPlaying } returns true
        every { mockPlayer.currentMediaItem?.mediaId } returns "1"

        playerControllerImpl.initPlayer(track, this)

        verify(exactly = 0) { mockPlayer.setMediaItem(any()) }
        verify(exactly = 0) { mockPlayer.prepare() }
    }

    @Test
    fun `onPlayPause should handle new track correctly`() {
        val track = Fixtures.audioTrack1
        every { mockPlayer.currentMediaItem?.mediaId } returns "1"

        playerControllerImpl.onPlayPause(track)

        verify { mockPlayer.stop() }
        verify { mockPlayer.setMediaItem(any()) }
        verify { mockPlayer.prepare() }
        verify { mockPlayer.playWhenReady = true }
    }

    @Test
    fun `onPlayPause should toggle playback for current track`() {
        val track = Fixtures.audioTrack1
        every { mockPlayer.currentMediaItem?.mediaId } returns "1"
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
