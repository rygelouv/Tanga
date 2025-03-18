package app.books.tanga.feature.read

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.DotsAnimation
import app.books.tanga.coreui.components.MarkdownText
import app.books.tanga.coreui.components.SystemBarsVisibility
import app.books.tanga.coreui.components.TangaPlayAudioFab
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.data.PreviewData
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.aiprompts.QuotesView
import app.books.tanga.feature.audioplayer.miniplayer.MiniPlayerAwareSpacer
import app.books.tanga.feature.read.components.ReadFontScaleChooser
import app.books.tanga.feature.summary.SummaryContentState
import app.books.tanga.feature.summary.details.SaveButton
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ReadSummaryScreen(
    state: ReadSummaryUiState,
    onNavigateToPreviousScreen: () -> Unit,
    onToggleFavorite: () -> Unit,
    onFontSizeClick: () -> Unit,
    onFontScaleChange: (Float) -> Unit,
    onNavigateToAudioPlayer: (SummaryId) -> Unit,
    modifier: Modifier = Modifier,
    shouldShowFavoriteButton: Boolean = true,
    contentHeaderInfo: ContentHeaderInfo? = null
) {
    SystemBarsVisibility(statusBarColor = MaterialTheme.colorScheme.onPrimaryContainer, statusBarVisible = true)

    val isVisible = rememberSaveable { mutableStateOf(true) }
    val scrollConnection = nestedScrollConnection { isVisible.value = it }

    when (state.progressState) {
        ProgressState.Show -> {
            LoadingAnimation(modifier = modifier)
        }

        ProgressState.Hide -> {
            Scaffold(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    .padding(bottom = 22.dp),
                topBar = {
                    ReadSummaryTopBar(
                        onBackClick = onNavigateToPreviousScreen,
                        isFavorite = state.summaryContentState.isFavorite,
                        fontSizeChooserVisible = state.fontSizeChooserVisible,
                        favoriteProgressState = state.summaryContentState.favoriteProgressState,
                        onToggleFavorite = onToggleFavorite,
                        shouldShowFavoriteButton = shouldShowFavoriteButton,
                        onFontSizeClick = onFontSizeClick
                    )
                },
                floatingActionButton = {
                    state.summaryContentState.summaryId?.let {
                        ReadSummaryFab(
                            isVisible = isVisible.value,
                            summaryId = it,
                            onClick = onNavigateToAudioPlayer
                        )
                    }
                }
            ) {
                Column(
                    modifier = Modifier.padding(it),
                ) {
                    contentHeaderInfo?.let {
                        ContentHeader(contentHeaderInfo = it)
                    }

                    ReadSummaryContent(
                        state = state,
                        nestedScrollConnection = scrollConnection,
                        onFontScaleChange = onFontScaleChange
                    )
                }
            }
        }
    }
}

@Composable
fun ContentHeader(
    contentHeaderInfo: ContentHeaderInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = contentHeaderInfo.icon),
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.large))
        Text(
            text = contentHeaderInfo.title,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun nestedScrollConnection(onVisibilityChange: (Boolean) -> Unit) = remember {
    object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            // Hide FAB
            if (available.y < -1) {
                onVisibilityChange(false)
            }

            // Show FAB
            if (available.y > 1) {
                onVisibilityChange(true)
            }

            return Offset.Zero
        }
    }
}

@Composable
fun ReadSummaryFab(
    isVisible: Boolean,
    summaryId: SummaryId,
    modifier: Modifier = Modifier,
    onClick: (SummaryId) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it * 2 }),
        exit = slideOutVertically(targetOffsetY = { it * 2 }),
    ) {
        TangaPlayAudioFab(
            modifier = modifier,
            onNavigateToAudioPlayer = {
                onClick(summaryId)
            }
        )
    }
}

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DotsAnimation(
            dotColor = Color.White
        )
    }
}

@Composable
fun ReadSummaryContent(
    state: ReadSummaryUiState,
    nestedScrollConnection: NestedScrollConnection,
    modifier: Modifier = Modifier,
    onFontScaleChange: (Float) -> Unit
) {
    val scrollState = rememberScrollState()
    val progress by remember {
        derivedStateOf {
            if (scrollState.maxValue > 0) {
                scrollState.value.toFloat() / scrollState.maxValue
            } else {
                0f
            }
        }
    }
    Column(
        modifier = modifier
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = MaterialTheme.colorScheme.tertiary,
            trackColor = MaterialTheme.colorScheme.primary,
        )
        AnimatedVisibility(visible = state.fontSizeChooserVisible) {
            ReadFontScaleChooser(
                onFontScaleChange = onFontScaleChange,
                initialValue = state.textScaleFactor.toProgressFloat()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                .nestedScroll(nestedScrollConnection)
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            when (state.contentType) {
                is ContentType.Markdown -> {
                    MarkdownText(
                        markdownText = state.contentType.markdownText,
                        textScale = state.textScaleFactor.value
                    )
                }
                is ContentType.Quotes -> {
                    QuotesView(
                        quotes = state.contentType.quotes.toImmutableList(),
                    )
                }
            }
//            state.summaryTextContent?.let {
//                MarkdownText(
//                    markdownText = it,
//                    textScale = state.textScaleFactor.value
//                )
//            }
            MiniPlayerAwareSpacer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadSummaryTopBar(
    isFavorite: Boolean,
    fontSizeChooserVisible: Boolean,
    favoriteProgressState: ProgressState,
    shouldShowFavoriteButton: Boolean,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onFontSizeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        title = { },
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    modifier = Modifier
                        .size(26.dp)
                        .testTag("back_button"),
                    painter = painterResource(id = TangaIcons.LeftArrow),
                    contentDescription = "back navigation"
                )
            }
        },
        actions = {
            IconButton(onClick = onFontSizeClick) {
                Icon(
                    modifier = Modifier
                        .size(26.dp)
                        .testTag("font_size"),
                    painter = painterResource(id = app.books.tanga.coreui.R.drawable.font_size),
                    tint = if (fontSizeChooserVisible) {
                        MaterialTheme.colorScheme.tertiary
                    } else {
                        Color.White
                    },
                    contentDescription = "adjust font size"
                )
            }
            if (shouldShowFavoriteButton) {
                SaveButton(
                    isSaved = isFavorite,
                    progressState = favoriteProgressState,
                    tintColor = Color.White,
                    onClick = { onToggleFavorite() }
                )
            }
        }
    )
}

@ExcludeFromJacocoGeneratedReport
@Preview
@Composable
private fun ReadSummaryScreenPreview() {
    TangaTheme {
        ReadSummaryScreen(
            state = ReadSummaryUiState(
                summaryContentState = SummaryContentState(
                    isFavorite = false,
                    favoriteProgressState = ProgressState.Hide
                ),
                summaryTextContent = PreviewData.SUMMARY_TEXT,
                progressState = ProgressState.Hide
            ),
            contentHeaderInfo = ContentHeaderInfo(
                icon = R.drawable.idea,
                title = "What are the most thought-provoking quotes from this book?"
            ),
            onNavigateToPreviousScreen = {},
            onToggleFavorite = {},
            onNavigateToAudioPlayer = {},
            onFontSizeClick = {},
            onFontScaleChange = {}
        )
    }
}
