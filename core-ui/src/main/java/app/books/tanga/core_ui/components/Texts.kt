package app.books.tanga.core_ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * This is a composable function that displays a description text with centered alignment and padding.
 * The text is styled with the bodyLarge typography and the color of the text is based on the Material theme.
 * @param modifier: An instance of [Modifier] that can be used to apply styling to the [Text] composable.
 * @param text: The text to be displayed as a description.
 */
@Composable
fun TangaDescriptionText(
    modifier: Modifier = Modifier,
    text: String,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 50,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        text = text,
        style = style,
        overflow = overflow,
        maxLines = maxLines,
        textAlign = textAlign
    )
}