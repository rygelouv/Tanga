package app.books.tanga.feature.summary.details

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState

@Composable
fun SaveButton(
    progressState: ProgressState,
    modifier: Modifier = Modifier,
    isSaved: Boolean = false,
    onClick: () -> Unit
) {
    IconButton(modifier = modifier.testTag("save_favorite"), onClick = {
        when (progressState) {
            ProgressState.Hide -> {
                onClick()
            }

            ProgressState.Show -> Unit
        }
    }) {
        when (progressState) {
            ProgressState.Hide -> {
                if (isSaved) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .testTag("remove_favorite_icon"),
                        painter = painterResource(id = R.drawable.ic_save_filled),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        contentDescription = "saved summary"
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .testTag("save_favorite_icon"),
                        painter = painterResource(id = R.drawable.ic_save),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        contentDescription = "save summary"
                    )
                }
            }

            ProgressState.Show -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(24.dp)
                        .testTag("saving_favorite_progress"),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    trackColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
