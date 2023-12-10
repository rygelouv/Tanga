package app.books.tanga.feature.summary.details

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.utils.shareSummary

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SummaryTopAppBar(
    summary: SummaryUi?,
    isFavorite: Boolean,
    favoriteProgressState: ProgressState,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    modifier = Modifier
                        .size(26.dp)
                        .testTag("back_button"),
                    painter = painterResource(id = TangaIcons.LeftArrow),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "back navigation"
                )
            }
        },
        actions = {
            SaveButton(
                isSaved = isFavorite,
                progressState = favoriteProgressState,
                onClick = { onSaveClick() }
            )
            val context = LocalContext.current
            IconButton(onClick = {
                summary?.let {
                    shareSummary(
                        context = context,
                        summaryTitle = it.title,
                        summaryAuthor = it.author
                    )
                }
            }) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .testTag("share"),
                    painter = painterResource(id = R.drawable.ic_share_2),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "share summary"
                )
            }
        }
    )
}
