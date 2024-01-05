package app.books.tanga.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.data.FakeData
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.ErrorContent
import app.books.tanga.feature.library.LibraryShimmerLoader
import app.books.tanga.feature.summary.list.SummaryGrid
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SearchScreen(
    state: SearchUiState,
    snackBarHostState: SnackbarHostState,
    onSearch: (String) -> Unit,
    onNavigateToPreviousScreen: () -> Unit,
    onCategorySelect: (CategoryUi) -> Unit,
    onCategoryUnselect: (CategoryUi) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToSummary: (SummaryId) -> Unit,
) {
    Scaffold(
        topBar = {
            SearchTopBar {
                onNavigateToPreviousScreen()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(14.dp)
        ) {
            SearchBox { query ->
                onSearch(query)
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))

            when (state.progressState) {
                ProgressState.Show -> {
                    state.categories?.let {
                        SearchCategoryHeader(
                            shouldShowCategories = state.shouldShowCategories,
                            categories = it.toImmutableList(),
                            selectedCategories = state.selectedCategories.toImmutableList(),
                            onCategorySelect = onCategorySelect,
                            onCategoryUnselect = onCategoryUnselect
                        )
                    }
                    LibraryShimmerLoader(modifier = Modifier.fillMaxWidth().testTag("shimmer_loader"))
                }

                ProgressState.Hide -> {
                    DisplayContent(state, onCategorySelect, onCategoryUnselect, onNavigateToSummary, onRetry)
                }
            }
        }
    }
}

@Composable
private fun DisplayContent(
    state: SearchUiState,
    onCategorySelect: (CategoryUi) -> Unit,
    onCategoryUnselect: (CategoryUi) -> Unit,
    onNavigateToSummary: (SummaryId) -> Unit,
    onRetry: () -> Unit
) {
    when {
        state.summaries?.isEmpty() == true && state.error == null -> {
            EmptySearchScreen(query = state.query ?: "")
        }

        state.summaries != null -> {
            val summaries = state.summaries.toImmutableList()
            SummaryGrid(
                modifier = Modifier,
                summaries = summaries,
                header = {
                    state.categories?.let {
                        SearchCategoryHeader(
                            shouldShowCategories = state.shouldShowCategories,
                            categories = it.toImmutableList(),
                            selectedCategories = state.selectedCategories.toImmutableList(),
                            onCategorySelect = onCategorySelect,
                            onCategoryUnselect = onCategoryUnselect
                        )
                    }
                }
            ) { id -> onNavigateToSummary(SummaryId(id)) }
        }

        state.error != null -> {
            ErrorContent(
                errorInfo = state.error.info,
                canRetry = true,
                onClick = { onRetry() }
            )
        }
    }
}

@Composable
fun SearchCategoryHeader(
    shouldShowCategories: Boolean?,
    categories: ImmutableList<CategoryUi>,
    selectedCategories: ImmutableList<CategoryUi>,
    onCategorySelect: (CategoryUi) -> Unit,
    onCategoryUnselect: (CategoryUi) -> Unit
) {
    if (shouldShowCategories == true) {
        CategoriesSection(
            categories = categories.toImmutableList(),
            selectedCategories = selectedCategories,
            onCategorySelect = { category ->
                onCategorySelect(category)
            },
            onCategoryUnselect = { category ->
                onCategoryUnselect(category)
            }
        )
    }
}

@Composable
private fun SearchTopBar(
    modifier: Modifier = Modifier,
    onNavigateToPreviousScreen: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 34.dp),
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
            text = stringResource(id = R.string.explore),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchBox(onSearch: (String) -> Unit) {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            active = false
            onSearch(text)
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.explore_search),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = TangaIcons.Search),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                Icon(
                    modifier =
                    Modifier
                        .size(16.dp)
                        .clickable {
                            text = ""
                            active = false
                            onSearch(text)
                        },
                    painter = painterResource(id = TangaIcons.Close),
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        },
        shape = RoundedCornerShape(size = 8.dp)
    ) {
    }
}

@Preview(device = "id:pixel_7")
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SearchScreenPreview() {
    val state = SearchUiState(
        query = "query",
        categories = FakeData.allCategories(),
        summaries = FakeData.allSummaries(),
        progressState = ProgressState.Hide
    )
    val snackBarHostState = SnackbarHostState()
    TangaTheme {
        SearchScreen(
            state = state,
            snackBarHostState = snackBarHostState,
            onSearch = {},
            onNavigateToPreviousScreen = {},
            onCategorySelect = {},
            onCategoryUnselect = {},
            onRetry = {},
            onNavigateToSummary = {}
        )
    }
}
