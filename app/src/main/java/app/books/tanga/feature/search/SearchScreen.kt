package app.books.tanga.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.core_ui.components.Tag
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.data.FakeData
import app.books.tanga.feature.summary.list.SummaryGrid

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen() {
    var summaries by remember {
        mutableStateOf(FakeData.allSummaries().shuffled())
    }
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(start = 14.dp, end = 14.dp, top = 44.dp, bottom = 14.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.explore),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.small))

        SearchBox()

        Spacer(modifier = Modifier.height(LocalSpacing.current.large))

        CategoriesSection {
            summaries = FakeData.allSummaries().shuffled()
        }

        Spacer(modifier = Modifier.height(LocalSpacing.current.large))

        SummaryGrid(Modifier, summaries, {})
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun CategoriesSection(
    onCategorySelected: (String) -> Unit = {}
) {

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
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small),
    ) {
        Tag(
            text = stringResource(id = R.string.business),
            modifier = Modifier.height(40.dp),
            shape = RoundedCornerShape(8.dp),
            hasBorder = true,
            icon = TangaIcons.Business,
            onSelected = { onCategorySelected("Business") }
        )
        Tag(
            text = stringResource(id = R.string.productivity),
            modifier = Modifier.height(40.dp),
            shape = RoundedCornerShape(8.dp),
            hasBorder = true,
            icon = TangaIcons.Productivity,
            onSelected = { onCategorySelected("Productivity") }
        )
        Tag(
            text = stringResource(id = R.string.self_development),
            modifier = Modifier.height(40.dp),
            shape = RoundedCornerShape(8.dp),
            hasBorder = true,
            icon = TangaIcons.SelfDevelopment,
            onSelected = { onCategorySelected("Self Development") }
        )
        Tag(
            text = stringResource(id = R.string.financial_education),
            modifier = Modifier.height(40.dp),
            shape = RoundedCornerShape(8.dp),
            hasBorder = true,
            icon = TangaIcons.FinancialEducation,
            onSelected = { onCategorySelected("Financial Education") }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchBox() {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = { text = it },
        onSearch = { active = false },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.explore_search),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = TangaIcons.Search),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
        },
        shape = RoundedCornerShape(size = 8.dp)
    ) {

    }
}
