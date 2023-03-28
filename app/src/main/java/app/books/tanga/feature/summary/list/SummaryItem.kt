package app.books.tanga.feature.summary.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.common.FakeData
import app.books.tanga.ui.theme.TangaLightGray2
import app.books.tanga.R
import app.books.tanga.ui.theme.TangaBluePale

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
            summaryCover = summaryUi.cover,
            onSummaryClicked = onSummaryClicked
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = summaryUi.title,
            fontSize = titleSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = TangaLightGray2,
            text = summaryUi.author,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold
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
                tint = TangaBluePale
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.summary_duration, summaryUi.duration),
                fontSize = 12.sp,
                color = TangaBluePale,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (summaryUi.hasVideo) {
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = R.drawable.ic_indicator_watch),
                contentDescription = null,
                tint = TangaBluePale
            )
        }
        if (summaryUi.hasGraphic) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = R.drawable.ic_indicator_mindmap),
                contentDescription = null,
                tint = TangaBluePale
            )
        }
    }
}

@Composable
fun SummaryImage(
    modifier: Modifier = Modifier,
    summaryCover: Int,
    onSummaryClicked: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 10.dp)
            .clickable { onSummaryClicked() },
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = summaryCover),
            contentDescription = "summary cover",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
fun SummaryItemPreview() {
    val summary = FakeData.allSummaries().first()
    SummaryItemBig(summary, {})
}