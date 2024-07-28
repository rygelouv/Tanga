package app.books.tanga.coreui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.icons.TangaIcons

/**
 * A simple top bar with a back navigation icon.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SimpleTopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
            .shadow(elevation = 1.dp),
        title = {},
        navigationIcon = {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = TangaIcons.LeftArrow),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    contentDescription = "back navigation"
                )
            }
        }
    )
}
