package app.books.tanga.feature.graphic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.components.DisplayGifAsset
import app.books.tanga.coreui.components.SystemBarsVisibility
import app.books.tanga.coreui.components.TangaPlayAudioFab
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.data.FakeData
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.SummaryContentState
import app.books.tanga.feature.summary.details.SaveButton

@Composable
fun GraphicSummaryScreen(
    state: GraphicSummaryUiState,
    onNavigateToPreviousScreen: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToAudioPlayer: (SummaryId) -> Unit
) {
    SystemBarsVisibility(statusBarColor = MaterialTheme.colorScheme.onPrimaryContainer)

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
                    GraphicSummaryTopBar(
                        onBackClick = onNavigateToPreviousScreen,
                        isFavorite = state.summaryContentState.isFavorite,
                        favoriteProgressState = state.summaryContentState.favoriteProgressState,
                        onToggleFavorite = onToggleFavorite
                    )
                },
                floatingActionButton = {
                    TangaPlayAudioFab(
                        onNavigateToAudioPlayer = {
                            state.summaryContentState.summaryId?.let {
                                onNavigateToAudioPlayer(it)
                            }
                        }
                    )
                }
            ) {
                GraphicSummaryContent(
                    modifier = Modifier.padding(it),
                    state = state
                )
            }
        }
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
        Text(
            text = "Diving into the summary",
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.large))
        DisplayGifAsset(assetName = "diving_dark_blue.gif")
    }
}

@Composable
fun GraphicSummaryContent(
    state: GraphicSummaryUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        state.summaryGraphicImage?.let {
            // TODO show zoomable image
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphicSummaryTopBar(
    isFavorite: Boolean,
    favoriteProgressState: ProgressState,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
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
            SaveButton(
                isSaved = isFavorite,
                progressState = favoriteProgressState,
                tintColor = Color.White,
                onClick = { onToggleFavorite() }
            )
        }
    )
}

@Preview
@Composable
fun GraphicSummaryScreenPreview() {
    TangaTheme {
        GraphicSummaryScreen(
            state = GraphicSummaryUiState(
                summaryContentState = SummaryContentState(
                    isFavorite = false,
                    favoriteProgressState = ProgressState.Hide
                ),
                summaryGraphicImage = FakeData.summaryText,
                progressState = ProgressState.Hide
            ),
            onNavigateToPreviousScreen = {},
            onToggleFavorite = {},
            onNavigateToAudioPlayer = {}
        )
    }
}
