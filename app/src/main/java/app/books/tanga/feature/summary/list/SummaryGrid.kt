package app.books.tanga.feature.summary.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.feature.summary.SummaryUi

@Composable
fun SummaryGrid(
    modifier: Modifier = Modifier,
    summaries: List<SummaryUi>,
    onSummaryClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.extraLarge),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        items(summaries) { summary ->
            SummaryItemBig(
                summary = summary,
                onSummaryClicked = onSummaryClicked
            )
        }
    }
}
