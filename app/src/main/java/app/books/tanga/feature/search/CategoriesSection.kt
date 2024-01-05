package app.books.tanga.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.components.Tag
import app.books.tanga.coreui.theme.LocalSpacing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSection(
    categories: ImmutableList<CategoryUi>,
    modifier: Modifier = Modifier,
    selectedCategories: ImmutableList<CategoryUi> = emptyList<CategoryUi>().toImmutableList(),
    onCategorySelect: (CategoryUi) -> Unit = {},
    onCategoryUnselect: (CategoryUi) -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.explore_categories),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.small))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
        ) {
            categories.forEach {
                Tag(
                    text = it.name,
                    modifier = Modifier.height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    hasBorder = true,
                    icon = it.icon,
                    isSelected = selectedCategories.contains(it),
                    onSelect = { onCategorySelect(it) },
                    onUnselect = { onCategoryUnselect(it) }
                )
            }
        }
    }
}
