package app.books.tanga.feature.audioplayer.infrastructure

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata

/**
 * Represents an audio track.
 *
 * @param id The unique identifier of the track.
 * @param title The title of the track. The book title.
 * @param author The author of the track. The book author.
 * @param coverUrl The URL of the cover image of the book.
 * @param url The URL of the track.
 */
data class AudioTrack(
    val id: String,
    val url: String,
    val title: String,
    val author: String,
    val coverUrl: String
)

/**
 * Converts an [AudioTrack] to a [MediaItem].
 */
fun AudioTrack.toMediaItem(): MediaItem = MediaItem.Builder()
    .setMediaId(id)
    .setUri(url)
    .setMediaMetadata(
        MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(author)
            .setArtworkUri(coverUrl.toUri())
            .build()
    )
    .build()
