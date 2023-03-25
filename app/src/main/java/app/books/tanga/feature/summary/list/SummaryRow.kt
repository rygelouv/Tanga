package app.books.tanga.feature.summary.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SummaryRow(summaries: List<SummaryUi>, onSummaryClicked: () -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(summaries) { summary ->
            SummaryItemSmall(summaryUi = summary, onSummaryClicked = onSummaryClicked)
        }
    }
}