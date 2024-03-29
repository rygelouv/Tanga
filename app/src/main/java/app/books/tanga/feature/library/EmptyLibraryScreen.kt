package app.books.tanga.feature.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.components.TangaButtonLeftIcon
import app.books.tanga.coreui.icons.TangaIcons

@Composable
fun EmptyLibraryScreen(modifier: Modifier = Modifier, onExploreButtonClick: () -> Unit) {
    Column(
        modifier = modifier.padding(top = 64.dp, bottom = 34.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.library_empty),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            textAlign = TextAlign.Center
        )
        Image(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = R.drawable.graphic_empty_library),
            contentDescription = "empty library image"
        )
        TangaButtonLeftIcon(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            onClick = onExploreButtonClick,
            rightIcon = TangaIcons.Search,
            text = stringResource(id = R.string.library_explore_summaries)
        )
    }
}
