package app.books.tanga.feature.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.common.FakeData
import app.books.tanga.ui.theme.TangaBlueDark
import app.books.tanga.ui.theme.TangaWhiteBackground

@Composable
fun LibraryScreen(onExploreButtonClicked: () -> Unit) {
    val summaries = FakeData.allSummaries()
    Column(
        modifier = Modifier
            .background(color = TangaWhiteBackground)
            .padding(start = 14.dp, end = 14.dp, top = 44.dp, bottom = 14.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.library),
            style = MaterialTheme.typography.h4,
            color = TangaBlueDark,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(22.dp))
        EmptyLibraryScreen(onExploreButtonClicked = onExploreButtonClicked)
        //SummaryGrid(Modifier, summaries)
    }
}

@Preview
@Composable
fun LibraryScreenPreview() {
    LibraryScreen({})
}