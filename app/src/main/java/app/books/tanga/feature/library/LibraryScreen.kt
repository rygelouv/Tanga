package app.books.tanga.feature.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.feature.summary.list.SummaryItem

/**
 * Shows the summaries saved by the user
 */
@Composable
fun LibraryScreen(
    onExploreButtonClicked: () -> Unit,
    onFavoriteClicked: (String) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier =
        Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(start = 14.dp, end = 14.dp, top = 44.dp, bottom = 14.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.library),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(22.dp))
        when (state.progressState) {
            ProgressState.Show -> {
                LibraryShimmerLoader()
            }
            ProgressState.Hide -> {
                if (state.favorites.isNullOrEmpty()) {
                    EmptyLibraryScreen(onExploreButtonClicked = onExploreButtonClicked)
                } else {
                    val favorites = state.favorites ?: return
                    FavoriteGrid(
                        favorites = favorites,
                        onFavoriteClicked = onFavoriteClicked
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteGrid(
    modifier: Modifier = Modifier,
    favorites: List<FavoriteUi>,
    onFavoriteClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.extraLarge),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        items(favorites) { favorite ->
            SummaryItem(
                summaryId = favorite.summaryId,
                title = favorite.title,
                author = favorite.author,
                coverUrl = favorite.coverUrl,
                duration = favorite.playingLength,
                // TODO: Add video indicator support
                hasVideo = false,
                // TODO: Add graphic indicator support
                hasGraphic = false,
                width = 134.dp,
                titleSize = 18.sp,
                onSummaryClicked = onFavoriteClicked
            )
        }
    }
}

@Preview
@Composable
fun LibraryScreenPreview() {
    LibraryScreen(
        onExploreButtonClicked = {},
        onFavoriteClicked = {}
    )
}
