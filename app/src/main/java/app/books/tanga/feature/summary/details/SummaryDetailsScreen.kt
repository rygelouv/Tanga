package app.books.tanga.feature.summary.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.common.ui.UrlDownloadableImage
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.ExpendableText
import app.books.tanga.coreui.components.ProfileImage
import app.books.tanga.coreui.components.SummaryActionButton
import app.books.tanga.coreui.components.TangaButtonLeftIcon
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.LocalTintColor
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.data.FakeData
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.ErrorContent
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.feature.summary.list.SummaryRow
import app.books.tanga.utils.openLink
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Suppress("LongParameterList")
@Composable
fun SummaryDetailsScreen(
    state: SummaryDetailsUiState,
    onBackClick: () -> Unit,
    onPlayClick: (SummaryId) -> Unit,
    onLoadSummary: (SummaryId) -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    onRecommendationClick: (SummaryId) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            SummaryTopAppBar(
                summary = state.summary,
                isFavorite = state.isFavorite,
                favoriteProgressState = state.favoriteProgressState,
                onBackClick = onBackClick,
                onSaveClick = { onToggleFavorite() }
            )
        },
        floatingActionButton = {
            state.summary?.id?.let { summaryId ->
                PlayFloatingActionButton(
                    summaryId = summaryId,
                    onClick = onPlayClick
                )
            }
        }
    ) { paddingValues ->
        when (state.progressState) {
            ProgressState.Show -> SummaryDetailsShimmerLoader()
            ProgressState.Hide ->
                SummaryDetailsContent(
                    state = state,
                    paddingValues = paddingValues,
                    onRecommendationClick = onRecommendationClick,
                    onErrorButtonClick = { state.summary?.id?.let { onLoadSummary(it) } }
                )
        }
    }
}

@Composable
private fun SummaryDetailsContent(
    state: SummaryDetailsUiState,
    paddingValues: PaddingValues,
    onRecommendationClick: (SummaryId) -> Unit,
    onErrorButtonClick: () -> Unit
) {
    state.summary?.let { summary ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SummaryDetailsHeader(modifier = Modifier.padding(paddingValues), summary = summary)

            Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))
            SummaryIntroduction(summary = summary)

            SummaryAuthor(
                modifier = Modifier.padding(horizontal = LocalSpacing.current.medium),
                author = summary.author,
                authorPictureUrl = summary.authorPictureUrl
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
            state.summary.purchaseBookUrl?.let { PurchaseButton(it) }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            Recommendations(
                recommendations = state.recommendations.toImmutableList(),
                onRecommendationClick = onRecommendationClick
            )
        }
    }

    state.error?.let {
        ErrorContent(
            errorInfo = it.info,
            canRetry = true,
            onClick = { onErrorButtonClick() }
        )
    }
}

@Composable
fun PlayFloatingActionButton(
    summaryId: SummaryId,
    modifier: Modifier = Modifier,
    onClick: (SummaryId) -> Unit
) {
    FloatingActionButton(
        modifier = modifier.testTag("play_button"),
        onClick = { onClick(summaryId) },
        containerColor = MaterialTheme.colorScheme.tertiary,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(18.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter =
            painterResource(
                id = app.books.tanga.coreui.R.drawable.ic_indicator_listen
            ),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "play summary"
        )
    }
}

@Composable
fun SummaryDetailsHeader(
    summary: SummaryUi,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier =
        Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.padding(LocalSpacing.current.medium),
                horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.large)
            ) {
                UrlDownloadableImage(
                    modifier = Modifier
                        .width(128.dp)
                        .testTag("summary_cover_image"),
                    summaryId = summary.id,
                    onSummaryClick = {}
                )
                SummaryBasicInfo(
                    title = summary.title,
                    author = summary.author,
                    duration = summary.duration
                )
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            SummaryActionButtonsSection(summary)

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        }
    }
}

@Composable
private fun SummaryActionButtonsSection(summary: SummaryUi) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SummaryActionButton(
            modifier = Modifier.testTag("read_button"),
            text = stringResource(id = R.string.summary_details_read),
            icon = TangaIcons.IndicatorRead,
            enabled = summary.textUrl?.isNotEmpty() == true
        ) {}
        SummaryActionButton(
            modifier = Modifier.testTag("listen_button"),
            text = stringResource(id = R.string.summary_details_listen),
            icon = TangaIcons.IndicatorListen,
            enabled = summary.audioUrl?.isNotEmpty() == true
        ) {}
        SummaryActionButton(
            modifier = Modifier.testTag("watch_button"),
            text = stringResource(id = R.string.summary_details_watch),
            icon = TangaIcons.IndicatorWatch,
            enabled = summary.videoUrl?.isNotEmpty() == true
        ) {}
        SummaryActionButton(
            modifier = Modifier.testTag("visualize_button"),
            text = stringResource(id = R.string.summary_details_visualize),
            icon = TangaIcons.IndicatorGraphic,
            enabled = summary.graphicUrl?.isNotEmpty() == true
        ) {}
    }
}

@Composable
private fun SummaryBasicInfo(
    title: String,
    author: String,
    duration: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = author,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
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
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SummaryIntroduction(
    summary: SummaryUi,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = LocalSpacing.current.medium)) {
        Text(
            text = stringResource(id = R.string.summary_details_introduction),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.small))

        summary.synopsis?.let {
            ExpendableText(text = it)
        }
    }
}

@Composable
fun SummaryAuthor(
    author: String,
    authorPictureUrl: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.small),
            text = stringResource(id = R.string.summary_details_author),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.small))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                modifier = Modifier.size(40.dp),
                tag = "author_image",
                photoUrl = authorPictureUrl,
                onClick = { }
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline,
                text = author,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun PurchaseButton(url: String) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        val context = LocalContext.current
        TangaButtonLeftIcon(
            modifier = Modifier.testTag("purchase_button"),
            text = "Purchase Book",
            rightIcon =
            app.books.tanga.coreui.R.drawable.ic_trolley,
            onClick = { openLink(context = context, url = url) }
        )
    }
}

@Composable
fun Recommendations(
    recommendations: ImmutableList<SummaryUi>,
    modifier: Modifier = Modifier,
    onRecommendationClick: (SummaryId) -> Unit
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
            onRecommendationClick(SummaryId(summaryId))
        }
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SummaryDetailsScreenPreview() {
    TangaTheme {
        SummaryDetailsScreen(
            state = SummaryDetailsUiState(
                summary = FakeData.allSummaries().first().copy(purchaseBookUrl = "https://www.google.com"),
                recommendations = FakeData.allSummaries().toImmutableList(),
                progressState = ProgressState.Hide,
                favoriteProgressState = ProgressState.Hide,
                isFavorite = false
            ),
            onBackClick = {},
            onPlayClick = {},
            onLoadSummary = {},
            onToggleFavorite = {},
            onRecommendationClick = {}
        )
    }
}
