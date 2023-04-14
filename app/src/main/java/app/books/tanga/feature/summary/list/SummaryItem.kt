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
import app.books.tanga.data.FakeData
import app.books.tanga.core_ui.components.SummaryImage
import app.books.tanga.core_ui.theme.LocalTintColor

@Composable
fun SummaryItemBig(summaryUi: SummaryUi, onSummaryClicked: () -> Unit) {
    SummaryItem(
        summaryUi = summaryUi,
        width = 134.dp,
        titleSize = 18.sp,
        onSummaryClicked = onSummaryClicked
    )
}

@Composable
fun SummaryItemSmall(summaryUi: SummaryUi, onSummaryClicked: () -> Unit) {
    SummaryItem(
        summaryUi = summaryUi,
        width = 120.dp,
        titleSize = 15.sp,
        onSummaryClicked = onSummaryClicked
    )
}

@Composable
fun SummaryItem(
    summaryUi: SummaryUi,
    width: Dp,
    titleSize: TextUnit,
    onSummaryClicked: () -> Unit
) {
    Column(
        modifier = Modifier.width(width),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SummaryImage(
            painter = painterResource(id = summaryUi.cover),
            onSummaryClicked = onSummaryClicked
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = summaryUi.title,
            fontSize = titleSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = summaryUi.author,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
        )
        SummaryIndicators(summaryUi)
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
                painter = painterResource(id = R.drawable.ic_indicator_listen),
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
                painter = painterResource(id = R.drawable.ic_indicator_watch),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
        }
        if (summaryUi.hasGraphic) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = R.drawable.ic_indicator_mindmap),
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