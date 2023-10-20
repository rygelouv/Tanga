package app.books.tanga.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.R.drawable.ic_right_arrow
import app.books.tanga.coreui.components.SummaryImage
import app.books.tanga.coreui.components.Tag
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.LocalGradientColors
import app.books.tanga.data.FakeData
import app.books.tanga.feature.summary.SummaryUi

@Composable
fun HomeTopCard(
    summaryUi: SummaryUi,
    modifier: Modifier = Modifier,
    onSummaryClick: (String) -> Unit
) {
    val gradientColors =
        listOf(
            LocalGradientColors.current.start,
            LocalGradientColors.current.center,
            LocalGradientColors.current.end
        )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .clickable {
                onSummaryClick(summaryUi.id.value)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = gradientColors)
                ).padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ContentColumn()

            SummaryImage(
                summaryId = summaryUi.id.value,
                modifier = Modifier.width(90.dp),
                painter = painterResource(id = summaryUi.cover),
                onSummaryClick = onSummaryClick
            )
        }
    }
}

@Composable
private fun ContentColumn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
        Tag(text = "Business", icon = TangaIcons.IndicatorGraphic)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.home_top_card_title),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
        ActionRow()
    }
}

@Composable
private fun ActionRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = Color.White,
            text = stringResource(id = R.string.home_top_card_action_text),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = ic_right_arrow),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopCardPreview() {
    HomeTopCard(summaryUi = FakeData.allSummaries().first(), onSummaryClick = {})
}
