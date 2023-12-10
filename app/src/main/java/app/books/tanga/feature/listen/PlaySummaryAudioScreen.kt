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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.coreui.components.GlideSummaryImage
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.audioplayer.PlaybackState
import app.books.tanga.feature.audioplayer.PlayerActions
import app.books.tanga.feature.audioplayer.PlayerState
import app.books.tanga.utils.toTimeFormat

@Composable
fun PlaySummaryAudioScreen(
    summaryId: SummaryId,
    modifier: Modifier = Modifier,
    viewModel: PlaySummaryAudioViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val actions: PlayerActions = viewModel

    LaunchedEffect(Unit) {
        viewModel.loadSummary(summaryId)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            PlaySummaryAudioTopBar(onBackClick = onBackClick)
        }
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PlaySummaryAudioContent(
                summaryId = state.summaryId,
                title = state.title,
                author = state.author,
                coverUrl = state.coverUrl,
                playbackState = state.playbackState,
                actions = actions
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaySummaryAudioTopBar(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    TopAppBar(modifier = modifier, title = {}, navigationIcon = {
        IconButton(onClick = { onBackClick() }) {
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
    actions: PlayerActions,
    modifier: Modifier = Modifier,
    summaryId: String? = null,
    title: String? = null,
    author: String? = null,
    coverUrl: String? = null,
    playbackState: PlaybackState? = null
) {
    Box(modifier = modifier.fillMaxSize()) {
        Surface(
            color = Color.White,
            modifier =
            Modifier
                .fillMaxSize()
                .offset(y = 148.dp),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))
                Spacer(modifier = Modifier.height(LocalSpacing.current.large))

                Box(
                    modifier =
                    Modifier
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
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(LocalSpacing.current.small))

                author?.let {
                    Text(
                        color = MaterialTheme.colorScheme.outline,
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))

                PlaybackControls(playbackState = playbackState, actions = actions)
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))

                AudioBar(playbackState = playbackState) { actions.onSeekBarPositionChanged(it) }
            }
        }
        GlideSummaryImage(
            summaryId = summaryId ?: "",
            modifier =
            Modifier
                .width(154.dp)
                .align(alignment = Alignment.TopCenter)
                .offset(y = 4.dp),
            url = coverUrl,
            onSummaryClick = { }
        )
    }
}

@Composable
private fun PlaybackControls(
    actions: PlayerActions,
    playbackState: PlaybackState? = null
) {
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
            style = MaterialTheme.typography.bodySmall
        )
        IconButton(onClick = { actions.onPlayPause() }, modifier = Modifier.size(64.dp)) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    modifier =
                    Modifier
                        .size(18.dp)
                        .padding(18.dp),
                    painter =
                    painterResource(
                        id = if (playbackState?.state == PlayerState.PLAYING) TangaIcons.Pause else TangaIcons.Play
                    ),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "play/pause"
                )
            }
        }
        Text(
            color = MaterialTheme.colorScheme.outline,
            text = "+15s",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
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
private fun AudioBar(
    playbackState: PlaybackState?,
    onSliderPositionChange: (Long) -> Unit
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        MediaSlider(playbackState = playbackState, onSliderPositionChange = onSliderPositionChange)
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                color = MaterialTheme.colorScheme.outline,
                text = playbackState?.position?.toTimeFormat() ?: "00:00",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                color = MaterialTheme.colorScheme.outline,
                text = playbackState?.duration?.toTimeFormat() ?: "00:00",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
