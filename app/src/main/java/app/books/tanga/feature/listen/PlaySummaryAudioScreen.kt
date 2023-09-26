package app.books.tanga.feature.listen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import app.books.tanga.core_ui.components.GlideSummaryImage
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.feature.audioplayer.PlaybackState
import app.books.tanga.feature.audioplayer.PlayerActions
import app.books.tanga.feature.audioplayer.PlayerState
import app.books.tanga.toTimeFormat

@Composable
fun PlaySummaryAudioScreen(
    summaryId: String,
    onBackClicked: () -> Unit, viewModel: PlaySummaryAudioViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val actions: PlayerActions = viewModel

    LaunchedEffect(Unit) {
        viewModel.loadSummary(summaryId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PlaySummaryAudioTopBar(onBackClicked)
        },
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PlaySummaryAudioContent(
                summaryId = state.summaryId,
                title = state.title,
                author = state.author,
                coverUrl = state.coverUrl,
                totalDuration = state.duration,
                playbackState = state.playbackState,
                actions = actions
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySummaryAudioTopBar(onBackClicked: () -> Unit) {
    TopAppBar(title = {}, navigationIcon = {
        IconButton(onClick = { onBackClicked() }) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = TangaIcons.LeftArrow),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                contentDescription = "back navigation"
            )
        }
    })
}

@Composable
fun PlaySummaryAudioContent(
    summaryId: String? = null,
    title: String? = null,
    author: String? = null,
    coverUrl: String? = null,
    totalDuration: String? = null,
    playbackState: PlaybackState? = null,
    actions: PlayerActions
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 148.dp),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))
                Spacer(modifier = Modifier.height(LocalSpacing.current.large))

                Box(
                    modifier = Modifier
                        .width(34.dp)
                        .height(4.dp)
                        .background(MaterialTheme.colorScheme.onTertiaryContainer)
                )

                Spacer(modifier = Modifier.height(LocalSpacing.current.large))

                title?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                Spacer(modifier = Modifier.height(LocalSpacing.current.small))

                author?.let {
                    Text(
                        color = MaterialTheme.colorScheme.outline,
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))

                PlaybackControls(playbackState = playbackState, actions = actions)
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))

                AudioBar(
                    playbackState = playbackState,
                    totalDuration = totalDuration
                )
            }
        }
        GlideSummaryImage(summaryId = summaryId ?: "",
            modifier = Modifier
                .width(154.dp)
                .align(alignment = Alignment.TopCenter)
                .offset(y = 4.dp),
            url = coverUrl,
            painter = if (coverUrl == null) {
                painterResource(id = R.drawable.cover_never_split_difference)
            } else null,
            onSummaryClicked = { }
        )
    }
}

@Composable
private fun PlaybackControls(playbackState: PlaybackState? = null, actions: PlayerActions) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { actions.onBackward() }) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = TangaIcons.Backward),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "previous"
            )
        }
        Text(
            color = MaterialTheme.colorScheme.outline,
            text = "-15s",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
        )
        IconButton(onClick = { actions.onPlayPause() }, modifier = Modifier.size(64.dp)) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(14.dp), painter = painterResource(
                        id = if (playbackState?.state == PlayerState.PLAYING) TangaIcons.Pause else TangaIcons.Play
                    ), tint = MaterialTheme.colorScheme.onPrimary, contentDescription = "play/pause"
                )
            }
        }
        Text(
            color = MaterialTheme.colorScheme.outline,
            text = "+15s",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
        )
        IconButton(onClick = { actions.onForward() }) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = TangaIcons.Forward),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "next"
            )
        }
    }
}

@Composable
private fun AudioBar(playbackState: PlaybackState?, totalDuration: String?) {
    var sliderPosition = playbackState?.position?.toFloat() ?: 0f
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Slider(modifier = Modifier.fillMaxWidth(),
            value = sliderPosition,
            onValueChange = { sliderPosition = it })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                color = MaterialTheme.colorScheme.outline,
                text = playbackState?.duration?.toTimeFormat() ?: "00:00",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                color = MaterialTheme.colorScheme.outline,
                text = totalDuration ?: "00:00",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
