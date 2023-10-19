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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import app.books.tanga.coreui.components.ProfileImage
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.data.FakeData
import app.books.tanga.errors.ErrorContent
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.feature.summary.list.SummaryRow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(
    onSearch: () -> Unit,
    onProfilePictureClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSummaryClicked: (String) -> Unit
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
                onProfilePictureClicked = onProfilePictureClicked,
                photoUrl = state.userPhotoUrl
            )
        }
    ) {
        LoadHomeContent(
            modifier = Modifier.padding(it),
            state = state,
            onSummaryClicked = onSummaryClicked,
            onErrorButtonClicked = { viewModel.onRetry() }
        )
    }
}

@Composable
fun LoadHomeContent(
    state: HomeUiState,
    onSummaryClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    onErrorButtonClicked: () -> Unit = {}
) {
    if (state.progressState == ProgressState.Show) {
        AnimatedShimmerLoader(modifier)
    } else {
        state.sections?.let {
            HomeContent(
                modifier = modifier,
                state = state,
                onSummaryClicked = onSummaryClicked,
                onErrorButtonClicked = onErrorButtonClicked
            )
        }
    }
}

@Composable
fun HomeTopBar(
    photoUrl: String?,
    onSearch: () -> Unit,
    onProfilePictureClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = TangaIcons.Search),
            contentDescription = "home search icon",
            tint = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.clickable { onSearch() }
        )
        Spacer(modifier = Modifier.weight(5f))
        ProfileImage(
            photoUrl = photoUrl,
            shape = CircleShape,
            size = 40.dp,
            hasBorder = true,
            onClick = onProfilePictureClicked
        )
    }
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onSummaryClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    onErrorButtonClicked: () -> Unit = {}
) {
    val dailySummary =
        remember {
            FakeData.allSummaries().first()
        }
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(0.dp)
    ) {
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        val userFirstName = state.userFirstName ?: stringResource(id = R.string.anonymous)
        Text(
            color = MaterialTheme.colorScheme.outline,
            text = getWelcomeMessage(firstName = userFirstName),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small),
            contentPadding = PaddingValues(top = 0.dp, bottom = 0.dp)
        ) {
            item {
                HomeTopCard(summaryUi = dailySummary, onSummaryClicked = onSummaryClicked)
            }
            state.sections?.let {
                items(it.size) { index ->
                    val section = state.sections[index]
                    HomeSection(
                        sectionTitle = section.title,
                        isFirst = index == 0,
                        summaries = section.summaries.toImmutableList(),
                        onSummaryClicked = onSummaryClicked
                    )
                }
            }
            state.error?.let {
                item {
                    ErrorContent(
                        errorInfo = it.info,
                        canRetry = true,
                        onClick = onErrorButtonClicked
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
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(firstName)
        }
    }
}

@Composable
fun HomeSection(
    sectionTitle: String,
    summaries: ImmutableList<SummaryUi>,
    modifier: Modifier = Modifier,
    isFirst: Boolean = false,
    onSummaryClicked: (String) -> Unit
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
                text = stringResource(id = R.string.home_see_all),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        SummaryRow(summaries = summaries.toImmutableList(), onSummaryClicked = onSummaryClicked)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeSectionPreview() {
    HomeSection(
        sectionTitle = "Personal Growth",
        isFirst = true,
        summaries = FakeData.allSummaries().toImmutableList()
    ) {}
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}, onSummaryClicked = {})
}
