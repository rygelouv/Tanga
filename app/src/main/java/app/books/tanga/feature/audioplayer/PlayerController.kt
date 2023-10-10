package app.books.tanga.feature.audioplayer

import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Interface that defines the primary actions and states for a media player controller.
 */
interface PlayerController : PlayerActions {
    /** Observable state of the playback (e.g., current position, duration). */
    val playbackState: StateFlow<PlaybackState>

    /**
     * Initializes the media player with the provided track.
     *
     * @param track The audio track to be played.
     * @param scope The coroutine scope for launching player-related coroutines.
     */
    fun initPlayer(
        track: AudioTrack,
        scope: CoroutineScope
    )

    /** Releases any resources associated with the player. */
    fun releasePlayer()

    companion object {
        /** Interval (in milliseconds) used for seeking forward or backward. */
        const val SEEK_INTERVAL_MS = 15000
    }
}

/**
 * Concrete implementation of PlayerController using ExoPlayer as the media playback engine.
 */
class ExoPlayerController @Inject constructor(
    private val player: ExoPlayer
) : PlayerController,
    Player.Listener {
    /** A state flow that emits the current playback state of the player. */
    private val _playbackState = MutableStateFlow(PlaybackState())
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    /** Job for updating the playback state at regular intervals. */
    private var playbackStateUpdateJob: Job? = null

    /** Coroutine scope provided during initialization. */
    private var scope: CoroutineScope? = null

    init {
        player.addListener(this)
    }

    /**
     * Starts a coroutine that updates the playback state at regular intervals.
     */
    private fun startPeriodicPlaybackUpdates() {
        playbackStateUpdateJob =
            scope?.launch {
                do {
                    _playbackState.update {
                        it.copy(position = player.currentPosition, duration = player.duration)
                    }
                    // Calculate delay to the next second boundary
                    val delayMillis = 1000 - (player.currentPosition % 1000)
                    delay(delayMillis)
                } while (playbackStateUpdateJob?.isActive == true &&
                    playbackState.value.state == PlayerState.PLAYING
                )
            }
    }

    private fun stopPeriodicPlaybackUpdates() {
        playbackStateUpdateJob?.cancel()
    }

    override fun initPlayer(
        track: AudioTrack,
        scope: CoroutineScope
    ) {
        this.scope = scope
        player.setMediaItem(track.toMediaItem())
        player.prepare()
    }

    override fun onPlayPause() {
        if (player.playbackState == Player.STATE_IDLE) player.prepare()
        player.playWhenReady = player.playWhenReady.not()
        if (player.playWhenReady) {
            startPeriodicPlaybackUpdates()
        } else {
            stopPeriodicPlaybackUpdates()
        }
    }

    override fun onForward() {
        player.seekTo(player.currentPosition + PlayerController.SEEK_INTERVAL_MS)
    }

    override fun onBackward() {
        player.seekTo(player.currentPosition - PlayerController.SEEK_INTERVAL_MS)
    }

    override fun onSeekBarPositionChanged(position: Long) {
        player.seekTo(position)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        updatePlaybackStateBasedOnPlayer(playbackState)
    }

    override fun onPlayWhenReadyChanged(
        playWhenReady: Boolean,
        reason: Int
    ) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        updatePlaybackStateBasedOnPlayer(player.playbackState)
    }

    /**
     * Updates the playback state based on the current state of the player.
     */
    private fun updatePlaybackStateBasedOnPlayer(playbackState: Int) {
        when (playbackState) {
            Player.STATE_BUFFERING -> _playbackState.update { it.copy(state = PlayerState.BUFFERING) }
            Player.STATE_ENDED -> {
                _playbackState.update { it.copy(state = PlayerState.ENDED) }
                stopPeriodicPlaybackUpdates()
            }

            Player.STATE_IDLE -> _playbackState.update { it.copy(state = PlayerState.IDLE) }
            Player.STATE_READY -> {
                if (player.playWhenReady) {
                    _playbackState.update { it.copy(state = PlayerState.PLAYING) }
                    startPeriodicPlaybackUpdates()
                } else {
                    _playbackState.update { it.copy(state = PlayerState.PAUSE) }
                    stopPeriodicPlaybackUpdates()
                }
            }
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        _playbackState.update { it.copy(state = PlayerState.ERROR) }
    }

    override fun releasePlayer() {
        stopPeriodicPlaybackUpdates()
        player.removeListener(this)
        player.release()
    }
}
