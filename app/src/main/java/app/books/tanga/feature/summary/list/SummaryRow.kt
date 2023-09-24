package app.books.tanga.feature.summary.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.books.tanga.feature.summary.SummaryUi

@Composable
fun SummaryRow(
    modifier: Modifier = Modifier,
    summaries: List<SummaryUi>,
    onSummaryClicked: (String) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(summaries) { summary ->
            SummaryItemSmall(summary = summary, onSummaryClicked = onSummaryClicked)
        }
    }
}