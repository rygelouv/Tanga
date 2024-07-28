package app.books.tanga.coreui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

private const val SHADOW_TEXT_ALPHA_VALUE = 0.75f

/**
 * This is a composable function that displays a title text with shadow.
 * https://handstandsam.com/2021/08/09/jetpack-compose-text-shadows/
 */
@Composable
fun TextWithShadow(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    style: TextStyle = MaterialTheme.typography.headlineSmall
) {
    Box(modifier = modifier) {
        TangaDescriptionText(
            text = text,
            color = Color.DarkGray,
            style = style,
            modifier = Modifier
                .offset(
                    x = 2.dp,
                    y = 2.dp
                )
                .alpha(SHADOW_TEXT_ALPHA_VALUE)
        )
        TangaDescriptionText(
            text = text,
            color = color,
            style = style,
        )
    }
}

/**
 * This is a composable function that displays a description text with centered alignment and padding.
 * The text is styled with the bodyLarge typography and the color of the text is based on the Material theme.
 * @param modifier: An instance of [Modifier] that can be used to apply styling to the [Text] composable.
 * @param text: The text to be displayed as a description.
 */
@Composable
fun TangaDescriptionText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 50,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        color = color,
        text = text,
        style = style,
        overflow = overflow,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

/**
 * Text that expends when clicked on "Show more" and collapses when clicked on "Show less"
 * Show the "Show more" button only if the text is more than 3 lines
 */
@Composable
fun ExpendableText(
    text: String,
    modifier: Modifier = Modifier
) {
    var isExpandable by remember { mutableStateOf(false) }
    var isExpended by remember { mutableStateOf(false) }

    Column(modifier = modifier.animateContentSize()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline,
            text = text,
            maxLines = if (isExpended) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            onTextLayout = { textLayoutResult ->
                isExpandable = textLayoutResult.hasVisualOverflow
            }
        )
        if (isExpandable || isExpended) {
            TextButton(modifier = Modifier.padding(0.dp), onClick = { isExpended = isExpended.not() }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = if (isExpandable) "Show more" else "Show less",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
