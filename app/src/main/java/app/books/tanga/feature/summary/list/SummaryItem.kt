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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.core_ui.components.GlideSummaryImage
import app.books.tanga.data.FakeData
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.core_ui.theme.LocalTintColor
import app.books.tanga.feature.summary.SummaryUi

@Composable
fun SummaryItemBig(summaryUi: SummaryUi, onSummaryClicked: (String) -> Unit) {
    SummaryItem(
        summary = summaryUi,
        width = 134.dp,
        titleSize = 18.sp,
        onSummaryClicked = onSummaryClicked
    )
}

@Composable
fun SummaryItemSmall(summaryUi: SummaryUi, onSummaryClicked: (String) -> Unit) {
    SummaryItem(
        summary = summaryUi,
        width = 120.dp,
        titleSize = 15.sp,
        onSummaryClicked = onSummaryClicked
    )
}

@Composable
fun SummaryItem(
    summary: SummaryUi,
    width: Dp,
    titleSize: TextUnit,
    onSummaryClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.width(width),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        GlideSummaryImage(
            summaryId = summary.id,
            url = summary.coverUrl,
            painter = if (summary.coverUrl == null) {
                painterResource(id = R.drawable.cover_never_split_difference)
            } else null,
            onSummaryClicked = onSummaryClicked,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = summary.title,
            fontSize = titleSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = summary.author,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
        )
        SummaryIndicators(summary)
    }
}

@Composable
fun SummaryIndicators(summaryUi: SummaryUi) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(2f),
        ) {
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = TangaIcons.IndicatorListen),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.summary_duration, summaryUi.duration),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (summaryUi.hasVideo) {
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = TangaIcons.IndicatorWatch),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
        }
        if (summaryUi.hasGraphic) {
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
fun SummaryItemPreview() {
    val summary = FakeData.allSummaries().first()
    SummaryItemBig(summary, {})
}