package app.books.tanga.feature.audioplayer

interface PlayerActions {

    /**
     * Called when the play/pause button is clicked.
     */
    fun onPlayPause()

    /**
     * Called when the forward button is clicked.
     */
    fun onForward()

    /**
     * Called when the backward button is clicked.
     */
    fun onBackward()

    /**
     * Called when the seek bar position is changed. The new position is provided as a parameter.
     *
     * @param position The new position of the seek bar.
     */
    fun onSeekBarPositionChanged(position: Long)
}