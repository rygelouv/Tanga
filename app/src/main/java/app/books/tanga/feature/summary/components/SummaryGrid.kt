package app.books.tanga.feature.summary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.data.FakeData
import app.books.tanga.feature.search.CategoriesSection
import app.books.tanga.feature.summary.SummaryUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

enum class SummaryGridItemType {
    HEADER,
    SUMMARY,
    FOOTER
}

data class SummaryGridItem(
    val summary: SummaryUi? = null,
    val type: SummaryGridItemType = SummaryGridItemType.SUMMARY,
    val span: GridItemSpan = GridItemSpan(1)
)

/**
 * A grid of [SummaryUi] with a header at the beginning.
 */
@Composable
fun SummaryGrid(
    summaries: ImmutableList<SummaryUi>,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    onSummaryClick: (String) -> Unit
) {
    val items = summaries.toSummaryGridItems()
    LazyVerticalGrid(
        modifier = modifier.testTag("SummaryGrid"),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.extraLarge),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        items(
            items = items,
            span = { item ->
                item.span
            }
        ) { item ->
            when (item.type) {
                SummaryGridItemType.HEADER -> header()
                SummaryGridItemType.FOOTER -> footer()
                SummaryGridItemType.SUMMARY -> item.summary?.let {
                    SummaryItemBig(
                        summary = it,
                        onSummaryClick = onSummaryClick
                    )
                }
            }
        }
    }
}

/**
 * Converts a list of [SummaryUi] to a list of [SummaryGridItem]
 * with a header and footer items at the beginning and end.
 * The header and footer items have a span of 2 so it takes the whole row.
 */
fun ImmutableList<SummaryUi>.toSummaryGridItems(): ImmutableList<SummaryGridItem> {
    val items = mutableListOf<SummaryGridItem>()
    items.add(SummaryGridItem(type = SummaryGridItemType.HEADER, span = GridItemSpan(2)))
    items.addAll(this.map { SummaryGridItem(summary = it) })
    items.add(SummaryGridItem(type = SummaryGridItemType.FOOTER, span = GridItemSpan(2)))
    return items.toImmutableList()
}

@Preview(showBackground = true)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SummaryGridPreview() {
    TangaTheme {
        SummaryGrid(
            summaries = FakeData.allSummaries().toImmutableList(),
            header = {
                CategoriesSection(categories = FakeData.allCategories().toImmutableList())
            },
            onSummaryClick = {}
        )
    }
}
