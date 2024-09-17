package app.books.tanga.feature.listen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.R
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.Shapes
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.audioplayer.AudioTrack
import app.books.tanga.feature.audioplayer.PlayerActions
import app.books.tanga.feature.audioplayer.PlayerState
import coil.compose.AsyncImage

@Composable
fun MiniPlayer(
    state: MiniPlayerUiState,
    actions: PlayerActions,
    onExpandPlayer: (SummaryId) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.audioTrack == null) return
    val audioTrack = state.audioTrack

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(82.dp).padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = Shapes.large
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f).clickable { state.summaryId?.let { onExpandPlayer(it) } },
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = state.audioTrack.coverUrl,
                    contentDescription = "CoverImage",
                    placeholder = painterResource(id = R.drawable.tanga_default_cover),
                    error = painterResource(id = R.drawable.tanga_default_cover),
                    modifier = Modifier
                        .width(50.dp)
                        .height(60.dp)
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Clickable area to expand to full player
                Column(
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = audioTrack.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1
                    )
                    Text(
                        text = audioTrack.author,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1
                    )
                }
            }

            // Play/Pause button
            IconButton(onClick = { actions.onPlayPause(audioTrack) }, Modifier.size(34.dp)) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(
                        id = if (state.playerState == PlayerState.PLAYING) TangaIcons.Pause else TangaIcons.Play
                    ),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "play/pause"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Close button
            IconButton(onClick = onDismiss, Modifier.size(28.dp)) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = TangaIcons.Close),
                    contentDescription = "Close mini player",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
fun MiniPlayerPreview() {
    class PlayerActionsPreview : PlayerActions {
        override fun onPlayPause(track: AudioTrack) {}
        override fun onForward() {
            TODO("Not yet implemented")
        }

        override fun onBackward() {
            TODO("Not yet implemented")
        }

        override fun onSeekBarPositionChanged(position: Long) {
            TODO("Not yet implemented")
        }
    }
    TangaTheme {
        MiniPlayer(
            state = MiniPlayerUiState(
                summaryId = SummaryId("1"),
                audioTrack = AudioTrack(
                    id = "1",
                    url = "https://www.example.com/audio.mp3",
                    title = "Title",
                    author = "Author",
                    coverUrl = "https://i.postimg.cc/tpbPhffw/Deep-Work-01-1-min.jpg",
                ),
                playerState = PlayerState.PLAYING,
            ),
            actions = PlayerActionsPreview(),
            onExpandPlayer = {},
            onDismiss = {}
        )
    }
}
