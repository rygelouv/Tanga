package app.books.tanga.feature.summary.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.InfoCard
import app.books.tanga.coreui.components.TangaButtonLeftIcon
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.data.FakeData
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.library.LibraryShimmerLoader
import app.books.tanga.feature.search.CategoryUi
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SummariesByCategoryScreen(
    state: SummariesByCategoryUiState,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToSummary: (SummaryId) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToSearch: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SummariesByCategoryTopBar(
                categoryName = state.categoryUi?.name ?: "",
                onNavigateToPreviousScreen = onNavigateToPreviousScreen,
            )
        }
    ) { paddingValues ->
        SummariesByCategoryContent(
            state = state,
            onNavigateToSummary = onNavigateToSummary,
            onNavigateToSearch = onNavigateToSearch,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun SummariesByCategoryTopBar(
    categoryName: String,
    modifier: Modifier = Modifier,
    onNavigateToPreviousScreen: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onNavigateToPreviousScreen() }
        ) {
            Icon(
                modifier = Modifier
                    .size(26.dp)
                    .testTag("back_button"),
                painter = painterResource(id = TangaIcons.LeftArrow),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                contentDescription = "back navigation"
            )
        }
        Spacer(modifier = Modifier.width(LocalSpacing.current.medium))

        Text(
            modifier = Modifier.weight(1f),
            text = categoryName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SummariesByCategoryContent(
    state: SummariesByCategoryUiState,
    onNavigateToSummary: (SummaryId) -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.progressState) {
        ProgressState.Show -> {
            LibraryShimmerLoader(modifier = modifier)
        }

        ProgressState.Hide -> {
            state.categoryUi?.let { categoryUi ->
                state.summaries?.let {
                    Column(
                        modifier = modifier
                            .padding(
                                horizontal = LocalSpacing.current.medium,
                                vertical = LocalSpacing.current.small
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        SummaryGrid(
                            modifier = Modifier.testTag("summaries_grid"),
                            summaries = it.toImmutableList(),
                            header = {
                                InfoCard(
                                    image = state.categoryUi.icon,
                                )
                            },
                            footer = {
                                Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))
                                TangaButtonLeftIcon(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    onClick = onNavigateToSearch,
                                    rightIcon = TangaIcons.Search,
                                    text = stringResource(id = R.string.library_explore_summaries)
                                )
                            },
                        ) { id -> onNavigateToSummary(SummaryId(id)) }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SummariesByCategoryScreenPreview() {
    TangaTheme {
        SummariesByCategoryScreen(
            state = SummariesByCategoryUiState(
                progressState = ProgressState.Hide,
                categoryUi = CategoryUi(
                    id = "1",
                    name = "Business",
                    icon = app.books.tanga.coreui.R.drawable.graphic_business_simple
                ),
                summaries = FakeData.allSummaries(),
                error = null
            ),
            onNavigateToPreviousScreen = {},
            onNavigateToSummary = {},
            onNavigateToSearch = {}
        )
    }
}
