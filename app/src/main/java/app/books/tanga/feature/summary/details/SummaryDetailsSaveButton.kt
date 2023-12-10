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
    IconButton(modifier = modifier.testTag("save_favorite"), onClick = { onClick() }) {
        when (progressState) {
            ProgressState.Hide -> {
                if (isSaved) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_save_filled),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        contentDescription = "saved summary"
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_save),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        contentDescription = "save summary"
                    )
                }
            }

            ProgressState.Show -> {
                CircularProgressIndicator(
                    modifier = Modifier.width(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    trackColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
