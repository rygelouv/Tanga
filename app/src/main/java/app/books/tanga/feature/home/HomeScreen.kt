package app.books.tanga.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import app.books.tanga.common.data.FakeData
import app.books.tanga.feature.summary.list.SummaryRow
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun HomeScreen(
    onSearch: () -> Unit,
    onProfilePictureClicked: () -> Unit,
    onSummaryClicked: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(14.dp),
        topBar = {
            HomeTopBar(
                onSearch = onSearch,
                onProfilePictureClicked = onProfilePictureClicked,
                state.userPhotoUrl
            )
        },
    ) {
        LoadHomeContent(
            modifier = Modifier.padding(it),
            state = state, onSummaryClicked = onSummaryClicked
        )
    }
}

@Composable
fun LoadHomeContent(modifier: Modifier, state: HomeUiState, onSummaryClicked: () -> Unit) {

    if (state.isLoading) {
        AnimatedShimmerLoader(modifier)
    } else {
        HomeContent(
            modifier = modifier,
            userFirstName = state.userFirstName ?: stringResource(id = R.string.anonymous),
            onSummaryClicked = onSummaryClicked
        )
    }
}

@Composable
fun HomeTopBar(
    onSearch: () -> Unit,
    onProfilePictureClicked: () -> Unit,
    photoUrl: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "home search icon",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.clickable { onSearch() }
        )
        Spacer(modifier = Modifier.weight(5f))
        Surface(
            modifier = Modifier
                .size(40.dp)
                .clickable { onProfilePictureClicked() },
            shape = CircleShape,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ) {
            Image(
                painter = if (photoUrl != null) {
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoUrl)
                            .build()
                    )
                } else {
                    painterResource(id = R.drawable.profile_placeholder)
                },
                contentDescription = "home profile picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    userFirstName: String,
    onSummaryClicked: () -> Unit
) {
    val dailySummary = remember {
        FakeData.allSummaries().first()
    }
    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            color = MaterialTheme.colorScheme.outline,
            text = getWelcomeMessage(firstName = userFirstName),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyColumn(modifier.fillMaxSize()) {
            item {
                HomeTopCard(dailySummary, onSummaryClicked)
            }
            item {
                HomeSection(
                    modifier = modifier,
                    sectionTitle = "Personal Growth",
                    isFirst = true,
                    onSummaryClicked = onSummaryClicked
                )
            }
            item {
                HomeSection(
                    modifier = modifier,
                    sectionTitle = "Financial education",
                    onSummaryClicked = onSummaryClicked
                )
            }
            item {
                HomeSection(
                    modifier = modifier,
                    sectionTitle = "Business",
                    onSummaryClicked = onSummaryClicked
                )
            }
            item {
                HomeSection(
                    modifier = modifier,
                    sectionTitle = "Psychology",
                    onSummaryClicked = onSummaryClicked
                )
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
    modifier: Modifier,
    sectionTitle: String,
    isFirst: Boolean = false,
    onSummaryClicked: () -> Unit
) {
    val summaries = remember {
        FakeData.allSummaries().shuffled().take(4)
    }
    Column {
        Spacer(modifier = Modifier.height(if (isFirst) 22.dp else 28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier.weight(2f),
                text = sectionTitle,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 20.sp,
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
        SummaryRow(summaries, onSummaryClicked)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeSectionPreview() {
    HomeSection(Modifier, "Personal Growth", true, {})
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}, {})
}