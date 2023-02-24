package app.books.tanga.feature.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.feature.FakeData
import app.books.tanga.ui.theme.TangaLightGray2

@Composable
fun SummaryItemBig(summaryUi: SummaryUi) {
    SummaryItem(summaryUi = summaryUi, width = 134.dp, titleSize = 18.sp)
}

@Composable
fun SummaryItemSmall(summaryUi: SummaryUi) {
    SummaryItem(summaryUi = summaryUi, width = 120.dp, titleSize = 14.sp)
}

@Composable
fun SummaryItem(summaryUi: SummaryUi, width: Dp, titleSize: TextUnit) {
    Column(
        modifier = Modifier.width(width),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SummaryImage(summaryUi.cover)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = summaryUi.title,
            fontSize = titleSize,
            style = MaterialTheme.typography.body1,
        )
        //Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = TangaLightGray2,
            text = summaryUi.author,
            fontSize = 14.sp,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SummaryImage(summaryCover: Int) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
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
    SummaryItemBig(summary)
}