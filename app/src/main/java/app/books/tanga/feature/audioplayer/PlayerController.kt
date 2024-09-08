package app.books.tanga.feature.audioplayer

import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.MoreExecutors
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        const val SEEK_INTERVAL_MS = 10000
    }
}

private const val SECOND_IN_MILLIS = 1000

/**
 * Concrete implementation of PlayerController using [MediaController] as the media playback engine.
 */
@Suppress("TooManyFunctions")
class PlayerControllerImpl @Inject constructor(
    controllerBuilder: MediaController.Builder
) : PlayerController, Player.Listener {
    /** A state flow that emits the current playback state of the player. */
    private val _playbackState = MutableStateFlow(PlaybackState())
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    /** Job for updating the playback state at regular intervals. */
    private var playbackStateUpdateJob: Job? = null

    /** Coroutine scope provided during initialization. */
    private var scope: CoroutineScope? = null

    private lateinit var player: Player

    init {
        val controllerFuture = controllerBuilder.buildAsync()
        controllerFuture.addListener({
            player = controllerFuture.get()
            player.addListener(this)
        }, MoreExecutors.directExecutor())
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
                    val delayMillis = SECOND_IN_MILLIS - (player.currentPosition % SECOND_IN_MILLIS)
                    delay(delayMillis)
                } while (playbackStateUpdateJob?.isActive == true &&
                    playbackState.value.state == PlayerState.PLAYING
                )
            }
    }

    private fun stopPeriodicPlaybackUpdates() {
        playbackStateUpdateJob?.cancel()
    }

    /**
     * Initializes the player with the provided track and starts playback based on the current state of the player.
     */
    override fun initPlayer(
        track: AudioTrack,
        scope: CoroutineScope
    ) {
        this.scope = scope

        val mediaItemId = player.currentMediaItem?.mediaId
        when {
            // If the player is already playing the same track, setup observation of the playback state.
            player.isPlaying && mediaItemId == track.id -> {
                startPeriodicPlaybackUpdates()
                updatePlaybackStateBasedOnPlayer(player.playbackState)
            }
            // If the player was playing a track and the user wants to play a new one,
            // do nothing until the user plays the new track.
            player.isPlaying && mediaItemId != track.id -> return
            // If player is not playing, prepare the new track.
            else -> prepareNewTrack(track)
        }
    }

    private fun prepareNewTrack(track: AudioTrack) {
        player.setMediaItem(track.toMediaItem())
        player.prepare()
    }

    /**
     * Handles the play/pause action based on the current state of the player.
     * If a different track is selected, it stops the current track and starts the new one.
     * If the player is idle, it prepares the track and starts playback.
     * If the player is playing, it pauses the playback.
     * If the player is paused, it resumes playback.
     */
    override fun onPlayPause(track: AudioTrack) {
        when {
            player.currentMediaItem?.mediaId != track.id -> handleNewTrack(track)
            player.playbackState == Player.STATE_IDLE -> handleIdleState()
            else -> togglePlayback()
        }
    }

    private fun handleNewTrack(track: AudioTrack) {
        stopPeriodicPlaybackUpdates()
        player.stop()
        prepareNewTrack(track)
        player.playWhenReady = true
        startPeriodicPlaybackUpdates()
    }

    private fun handleIdleState() {
        player.prepare()
        player.playWhenReady = true
        startPeriodicPlaybackUpdates()
    }

    private fun togglePlayback() {
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
