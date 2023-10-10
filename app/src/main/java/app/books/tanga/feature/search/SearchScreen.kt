package app.books.tanga.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.core_ui.components.Tag
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.errors.ErrorContent
import app.books.tanga.errors.ShowSnackbarError
import app.books.tanga.feature.library.LibraryShimmerLoader
import app.books.tanga.feature.summary.list.SummaryGrid

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.events.collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->

        HandleEvents(event = event, snackbarHostState = snackbarHostState)

        Column(
            modifier =
            Modifier
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(start = 14.dp, end = 14.dp, top = 44.dp, bottom = 14.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.explore),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.small))

            SearchBox { viewModel.onSearch(it) }

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))

            if (state.shouldShowCategories) {
                state.categories?.let {
                    CategoriesSection(
                        categories = it,
                        onCategorySelected = { category ->
                            viewModel.onCategorySelected(category)
                        },
                        onCategoryDeselected = { category ->
                            viewModel.onCategoryDeselected(category)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))

            when (state.progressState) {
                ProgressState.Show ->
                    LibraryShimmerLoader(
                        modifier = Modifier.fillMaxWidth()
                    )

                ProgressState.Hide -> {
                    if (state.summaries.isNullOrEmpty()) {
                        EmptySearchScreen(query = state.query ?: "")
                    } else {
                        state.summaries?.let {
                            SummaryGrid(
                                modifier = Modifier,
                                summaries = it
                            ) {}
                        }
                    }
                    state.error?.let {
                        ErrorContent(
                            errorInfo = it.info,
                            canRetry = true,
                            onClick = { viewModel.onRetry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HandleEvents(
    event: SearchUiEvent?,
    snackbarHostState: SnackbarHostState
) {
    when (event) {
        is SearchUiEvent.ShowSnackError -> {
            ShowSnackbarError(errorInfo = event.error.info, snackbarHostState = snackbarHostState)
        }

        null -> Unit
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun CategoriesSection(
    categories: List<CategoryUi>,
    onCategoryDeselected: (CategoryUi) -> Unit = {},
    onCategorySelected: (CategoryUi) -> Unit = {}
) {
    Text(
        text = stringResource(id = R.string.explore_categories),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(LocalSpacing.current.small))

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
    ) {
        categories.forEach {
            Tag(
                text = it.name,
                modifier = Modifier.height(40.dp),
                shape = RoundedCornerShape(8.dp),
                hasBorder = true,
                icon = it.icon,
                onSelected = { onCategorySelected(it) },
                onDeselected = { onCategoryDeselected(it) }
            )
        }
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
