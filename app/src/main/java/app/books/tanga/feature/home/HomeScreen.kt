package app.books.tanga.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.ProfileImage
import app.books.tanga.coreui.components.SearchButton
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.data.FakeData
import app.books.tanga.entity.CategoryId
import app.books.tanga.errors.ErrorContent
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.feature.summary.list.SummaryRow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(
    onSearch: () -> Unit,
    onProfilePictureClick: () -> Unit,
    onNavigateToSummariesByCategory: (CategoryId, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSummaryClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(14.dp),
        topBar = {
            HomeTopBar(
                onSearch = onSearch,
                onProfilePictureClick = onProfilePictureClick,
                photoUrl = state.userPhotoUrl
            )
        }
    ) {
        LoadHomeContent(
            modifier = Modifier.padding(it),
            state = state,
            onSummaryClick = onSummaryClick,
            onErrorButtonClick = { viewModel.onRetry() },
            onSeeAllClick = onNavigateToSummariesByCategory
        )
    }
}

@Composable
fun LoadHomeContent(
    state: HomeUiState,
    onSummaryClick: (String) -> Unit,
    onSeeAllClick: (CategoryId, String) -> Unit,
    modifier: Modifier = Modifier,
    onErrorButtonClick: () -> Unit = {}
) {
    if (state.progressState == ProgressState.Show) {
        AnimatedShimmerLoader(modifier)
    } else {
        state.sections?.let {
            HomeContent(
                modifier = modifier,
                state = state,
                onSummaryClick = onSummaryClick,
                onErrorButtonClick = onErrorButtonClick,
                onSeeAllClick = onSeeAllClick
            )
        }
    }
}

@Composable
fun HomeTopBar(
    photoUrl: String?,
    onSearch: () -> Unit,
    onProfilePictureClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchButton(onSearch = onSearch)

        Spacer(modifier = Modifier.weight(5f))

        ProfileImage(
            photoUrl = photoUrl,
            shape = CircleShape,
            size = 40.dp,
            hasBorder = true,
            onClick = onProfilePictureClick
        )
    }
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onSummaryClick: (String) -> Unit,
    onSeeAllClick: (CategoryId, String) -> Unit,
    modifier: Modifier = Modifier,
    onErrorButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(0.dp)
    ) {
        Spacer(modifier = Modifier.height(LocalSpacing.current.large))
        val userFirstName = state.userFirstName ?: stringResource(id = R.string.anonymous)
        Text(
            color = MaterialTheme.colorScheme.outline,
            text = getWelcomeMessage(firstName = userFirstName),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small),
            contentPadding = PaddingValues(top = 0.dp, bottom = 0.dp)
        ) {
            state.weeklySummary?.let {
                item {
                    HomeTopCard(summaryUi = it, onSummaryClick = onSummaryClick)
                }
            }
            state.sections?.let {
                items(it.size) { index ->
                    val section = state.sections[index]
                    HomeSection(
                        sectionId = section.categoryId,
                        sectionTitle = section.title,
                        isFirst = index == 0,
                        summaries = section.summaries.toImmutableList(),
                        onSummaryClick = onSummaryClick,
                        onSeeAllClick = onSeeAllClick
                    )
                }
            }
            state.error?.let {
                item {
                    ErrorContent(
                        errorInfo = it.info,
                        canRetry = true,
                        onClick = onErrorButtonClick
                    )
                }
            }
        }
    }
}

@Composable
@ReadOnlyComposable
fun getWelcomeMessage(firstName: String): AnnotatedString {
    val welcomeMessage = stringResource(id = R.string.home_welcome_message, firstName)
    val firstPart = welcomeMessage.replace(firstName, "", ignoreCase = true)
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.outline)) {
            append(firstPart)
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) {
            append(firstName)
        }
    }
}

@Composable
fun HomeSection(
    sectionId: CategoryId,
    sectionTitle: String,
    summaries: ImmutableList<SummaryUi>,
    onSeeAllClick: (CategoryId, String) -> Unit,
    modifier: Modifier = Modifier,
    isFirst: Boolean = false,
    onSummaryClick: (String) -> Unit
) {
    Column {
        Spacer(modifier = modifier.height(if (isFirst) 22.dp else 28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = sectionTitle,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.clickable { onSeeAllClick(sectionId, sectionTitle) },
                text = stringResource(id = R.string.home_see_all),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        SummaryRow(summaries = summaries.toImmutableList(), onSummaryClick = onSummaryClick)
    }
}

@Preview(showBackground = true)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun HomeSectionPreview() {
    HomeSection(
        sectionId = CategoryId("1"),
        sectionTitle = "Personal Growth",
        isFirst = true,
        summaries = FakeData.allSummaries().toImmutableList(),
        onSeeAllClick = { _, _ -> },
        onSummaryClick = {}
    )
}

@Preview(showBackground = true)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun HomeScreenPreview() {
    TangaTheme {
        HomeScreen(
            onSearch = {},
            onProfilePictureClick = {},
            onNavigateToSummariesByCategory = { _, _ -> },
            onSummaryClick = {}
        )
    }
}
