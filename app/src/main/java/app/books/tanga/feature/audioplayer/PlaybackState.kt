package app.books.tanga.feature.audioplayer

/**
 * Represents the current playback state of the player.
 *
 * @param state The current state of the player.
 * @param position The current position of the player.
 * @param duration The duration of the current media.
 */
data class PlaybackState(
    val state: PlayerState = PlayerState.IDLE,
    val position: Long = 0,
    val duration: Long = 0,
)


/**
 * Represents the current state of the player.
 */
enum class PlayerState {
    /**
     * State when the player is idle, not ready to play.
     */
    IDLE,

    /**
     * State when the player is ready to start playback.
     */
    READY,

    /**
     * State when the player is buffering content.
     */
    BUFFERING,

    /**
     * State when the player is actively playing content.
     */
    PLAYING,

    /**
     * State when the player has paused the playback.
     */
    PAUSE,

    /**
     * State when the player has encountered an error.
     */
    ERROR,

    /**
     * State when the playback has ended.
     */
    ENDED,
}