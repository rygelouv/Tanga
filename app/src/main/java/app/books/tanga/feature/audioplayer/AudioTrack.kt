package app.books.tanga.feature.audioplayer

import androidx.media3.common.MediaItem

/**
 * Represents an audio track.
 *
 * @param id The unique identifier of the track.
 * @param url The URL of the track.
 */
data class AudioTrack(
    val id: String,
    val url: String
)

/**
 * Converts an [AudioTrack] to a [MediaItem].
 */
fun AudioTrack.toMediaItem(): MediaItem = MediaItem.fromUri(url)
