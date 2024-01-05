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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.common.ui.UrlDownloadableImage
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalTintColor
import app.books.tanga.data.FakeData
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.SummaryUi

@Composable
fun SummaryItemBig(
    summary: SummaryUi,
    onSummaryClick: (String) -> Unit
) {
    SummaryItem(
        summaryId = summary.id,
        title = summary.title,
        author = summary.author,
        duration = summary.duration,
        hasVideo = summary.hasVideo,
        hasGraphic = summary.hasGraphic,
        width = 134.dp,
        titleSize = 18.sp,
        onSummaryClick = onSummaryClick
    )
}

@Composable
fun SummaryItemSmall(
    summary: SummaryUi,
    onSummaryClick: (String) -> Unit
) {
    SummaryItem(
        summaryId = summary.id,
        title = summary.title,
        author = summary.author,
        duration = summary.duration,
        hasVideo = summary.hasVideo,
        hasGraphic = summary.hasGraphic,
        width = 120.dp,
        titleSize = 14.sp,
        onSummaryClick = onSummaryClick
    )
}

@Suppress("LongParameterList", "FunctionNaming")
@Composable
fun SummaryItem(
    summaryId: SummaryId,
    title: String,
    author: String,
    duration: String,
    hasVideo: Boolean,
    hasGraphic: Boolean,
    width: Dp,
    titleSize: TextUnit,
    modifier: Modifier = Modifier,
    onSummaryClick: (String) -> Unit
) {
    Column(
        modifier = modifier.width(width),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        UrlDownloadableImage(
            summaryId = summaryId,
            onSummaryClick = onSummaryClick,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            fontSize = titleSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = author,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium
        )
        SummaryIndicators(
            duration = duration,
            hasVideo = hasVideo,
            hasGraphic = hasGraphic
        )
    }
}

@Composable
fun SummaryIndicators(
    duration: String,
    hasVideo: Boolean,
    hasGraphic: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(2f)
        ) {
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = TangaIcons.IndicatorListen),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.summary_duration, duration),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (hasVideo) {
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = TangaIcons.IndicatorWatch),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
        }
        if (hasGraphic) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = TangaIcons.IndicatorGraphic),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
        }
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SummaryItemPreview() {
    val summary = FakeData.allSummaries().first()
    SummaryItemBig(summary, {})
}
