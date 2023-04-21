package app.books.tanga.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.core_ui.theme.LocalTintColor

/**
 * This is a composable function that displays a tag with an icon and text.
 *
 * @param modifier: An instance of [Modifier] that can be used to apply styling to the [Row] composable.
 * @param text: The text to be displayed as a tag.
 * @param icon: The icon to be displayed as a tag.
 * @param tint: The tint of the icon.
 */
@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.onPrimary,
    tint: Color = LocalTintColor.current.color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .padding(start = 10.dp, end = 10.dp, bottom = 3.dp),
    ) {
        Icon(
            modifier = Modifier.size(13.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.small))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = tint,
            modifier = Modifier.padding(top = LocalSpacing.current.extraSmall),
            textAlign = TextAlign.Center
        )
    }
}

