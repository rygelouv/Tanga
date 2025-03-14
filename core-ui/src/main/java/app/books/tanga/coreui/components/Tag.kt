package app.books.tanga.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.LocalTintColor

/**
 * This is a composable function that displays a tag with an icon and text.
 *
 * @param modifier: An instance of [Modifier] that can be used to apply styling to the [Row] composable.
 * @param text: The text to be displayed as a tag.
 * @param icon: The icon to be displayed as a tag.
 * @param tint: The tint of the icon.
 */
@Suppress("CognitiveComplexMethod", "LongParameterList")
@Composable
fun Tag(
    text: String,
    icon: Int,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.onPrimary,
    tint: Color = LocalTintColor.current.color,
    hasBorder: Boolean = false,
    isSelectable: Boolean = true,
    isSelected: Boolean = false,
    contentSize: ContentSize = ContentSize.Small,
    onSelect: () -> Unit = {},
    onUnselect: () -> Unit = {}
) {
    var selected by remember { mutableStateOf(isSelected) }

    val borderModifier = if (hasBorder) Modifier.border(1.dp, tint, shape) else Modifier
    val backgroundModifier =
        Modifier.background(color = if (selected) tint else backgroundColor, shape = shape)
    val paddingModifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = contentSize.bottomPadding.dp)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        modifier
            .then(backgroundModifier)
            .then(borderModifier)
            .then(paddingModifier)
            .clickable {
                if (isSelectable.not()) return@clickable
                selected = selected.not()
                if (selected) onSelect() else onUnselect()
            }
    ) {
        Icon(
            modifier = Modifier.size(contentSize.iconSize.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (selected) Color.White else tint
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.small))
        Text(
            text = text,
            fontSize = contentSize.textSize.sp,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) Color.White else tint,
            modifier = Modifier.padding(top = LocalSpacing.current.extraSmall),
            textAlign = TextAlign.Start,
            lineHeight = contentSize.lineHeight.sp
        )
    }
}

@Suppress("MagicNumber")
enum class ContentSize(
    val iconSize: Int,
    val textSize: Int,
    val bottomPadding: Int,
    val lineHeight: Int
) {
    Small(16, 12, 4, 16),
    Medium(22, 14, 8, 22),
}
