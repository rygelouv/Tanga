package app.books.tanga.feature.summary.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.books.tanga.R
import app.books.tanga.core_ui.components.GlideSummaryImage
import app.books.tanga.core_ui.components.SummaryActionButton
import app.books.tanga.core_ui.components.TangaButtonLeftIcon
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.core_ui.theme.LocalTintColor
import app.books.tanga.data.FakeData
import app.books.tanga.feature.summary.list.SummaryRow
import app.books.tanga.feature.summary.SummaryUi

@Composable
fun SummaryDetailsScreen(
    summaryId: String,
    viewModel: SummaryDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onPlayClicked: () -> Unit,
    onRecommendationClicked: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadSummary(summaryId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            SummaryTopAppBar(onBackClicked)
        },
        floatingActionButton = { PlayFloatingActionButton(onClick = onPlayClicked) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val summary = state.summary ?: return@Column // Show error
            SummaryDetailsHeader(modifier = Modifier.padding(it), summary = summary)

            Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))
            SummaryIntroduction(summary = summary)

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
            PurchaseButton()

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            Recommendations(
                recommendations = state.recommendations,
                onRecommendationClicked = onRecommendationClicked
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SummaryTopAppBar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onBackClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = TangaIcons.LeftArrow),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "back navigation"
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_save),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "save summary"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_share_2),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "share summary"
                )
            }
        }
    )
}

@Composable
fun PlayFloatingActionButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.tertiary,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(18.dp),
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = app.books.tanga.core_ui.R.drawable.ic_indicator_listen),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "play summary"
        )
    }
}

@Composable
fun SummaryDetailsHeader(modifier: Modifier, summary: SummaryUi) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = modifier.padding(LocalSpacing.current.medium),
                horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.large)
            ) {
                GlideSummaryImage(
                    modifier = Modifier.width(128.dp),
                    summaryId = summary.id,
                    model = summary.coverUrl,
                    painter = if (summary.coverUrl == null) {
                        painterResource(id = R.drawable.cover_never_split_difference)
                    } else null,
                    onSummaryClicked = {},
                )
                SummaryBasicInfo(
                    title = summary.title,
                    author = summary.author,
                    duration = summary.duration
                )
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryActionButton(
                    text = stringResource(id = R.string.summary_details_read),
                    icon = TangaIcons.IndicatorRead,
                    enabled = summary.textUrl?.isNotEmpty() == true
                ) {}
                SummaryActionButton(
                    text = stringResource(id = R.string.summary_details_listen),
                    icon = TangaIcons.IndicatorListen,
                    enabled = summary.audioUrl?.isNotEmpty() == true
                ) {}
                SummaryActionButton(
                    text = stringResource(id = R.string.summary_details_watch),
                    icon = TangaIcons.IndicatorWatch,
                    enabled = summary.videoUrl?.isNotEmpty() == true
                ) {}
                SummaryActionButton(
                    text = stringResource(id = R.string.summary_details_visualize),
                    icon = TangaIcons.IndicatorGraphic,
                    enabled = summary.graphicUrl?.isNotEmpty() == true
                ) {}
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        }
    }
}

@Composable
private fun SummaryBasicInfo(
    title: String,
    author: String,
    duration: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = author,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = TangaIcons.IndicatorListen),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.summary_duration, duration),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
fun SummaryIntroduction(modifier: Modifier = Modifier, summary: SummaryUi) {
    Column(modifier = modifier.padding(horizontal = LocalSpacing.current.medium)) {
        Text(
            text = stringResource(id = R.string.summary_details_introduction),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        summary.synopsis?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline,
                text = it,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun PurchaseButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        TangaButtonLeftIcon(
            text = "Purchase Book",
            rightIcon = app.books.tanga.core_ui.R.drawable.ic_trolley,
            onClick = { /*TODO*/ }
        )
    }
}


@Composable
fun Recommendations(
    modifier: Modifier = Modifier,
    recommendations: List<SummaryUi>,
    onRecommendationClicked: (String) -> Unit
) {
    Column(modifier = modifier.padding(LocalSpacing.current.medium)) {
        Text(
            text = stringResource(id = R.string.summary_details_recommendations),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        SummaryRow(
            summaries = recommendations
        ) { summaryId ->
            onRecommendationClicked(summaryId)
        }
    }
}

@Preview
@Composable
fun SummaryHeaderPreview() {
    SummaryDetailsHeader(modifier = Modifier, FakeData.allSummaries().first())
}