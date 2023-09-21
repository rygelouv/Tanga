package app.books.tanga.core_ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.books.tanga.core_ui.theme.LocalSpacing
import app.books.tanga.core_ui.theme.LocalTintColor
import app.books.tanga.core_ui.theme.button

/**
 * This is a composable that displays a button with text and an icon on the right side.
 * The button has a rounded corner shape and a height of 64dp. When clicked, it triggers the onClick function.
 *
 * @param text: The text to be displayed on the button.
 * @param leftIcon: The resource id of the icon to be displayed on the left side of the button.
 * @param onClick: The function to be executed when the button is clicked.
 * @param height: The height of the button. Default value is 64dp.
 * @param elevation: The elevation of the button. Default value is 0dp.
 * @param endPadding: The padding on the right side of the button. Default value is 30dp.
 * @param iconSize: The size of the icon. Default value is 26dp.
 * @param shape: The shape of the button. Default value is RoundedCornerShape(16.dp).
 * */
@Composable
fun TangaButtonRightIcon(
    modifier: Modifier = Modifier,
    text: String,
    leftIcon: Int,
    onClick: () -> Unit,
    height: Dp = 64.dp,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
    endPadding: Dp = 30.dp,
    iconSize: Dp = 26.dp,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = shape,
        elevation = elevation
    ) {
        Row(
            modifier = Modifier.padding(end = endPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.button
            )
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = leftIcon),
                contentDescription = "explore summaries icon"
            )
        }
    }
}

/**
 * This is a composable function that displays a button with text and an icon on the left side.
 * The button has a rounded corner shape and a height of 64dp. When clicked, it triggers the onClick function.
 *
 * @param text: The text to be displayed on the button.
 * @param rightIcon: The resource id of the icon to be displayed on the right side of the button.
 * @param onClick: The function to be executed when the button is clicked.
 * @param height: The height of the button. Default value is 64dp.
 * @param elevation: The elevation of the button. Default value is 0dp.
 * @param startPadding: The padding on the left side of the button. Default value is 30dp.
 * @param iconSize: The size of the icon. Default value is 26dp.
 * @param shape: The shape of the button. Default value is RoundedCornerShape(16.dp).
 * */
@Composable
fun TangaButtonLeftIcon(
    modifier: Modifier = Modifier,
    text: String,
    rightIcon: Int,
    onClick: () -> Unit,
    height: Dp = 64.dp,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
    startPadding: Dp = 30.dp,
    iconSize: Dp = 26.dp,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = shape,
        elevation = elevation
    ) {
        Box(modifier = Modifier.padding(start = startPadding)) {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = rightIcon),
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.button
            )
        }
    }
}

/**
 * This is a composable function that displays a button with icon and text at the bottom.
 * @param text: The text to be displayed on the button.
 * @param icon: The resource id of the icon to be displayed on the button.
 * @param onClick: The function to be executed when the button is clicked.
 * @param enabled : Whether the button is enabled or not. Default value is true.
 * When the button is disabled, it is not clickable and the icon and text are grayed out.
 */
@Composable
fun SummaryActionButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Column(
        modifier = if (enabled) modifier.clickable { onClick() } else modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (enabled) LocalTintColor.current.color else LocalTintColor.current.disabled
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.small))

        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled) MaterialTheme.colorScheme.primary else LocalTintColor.current.disabled,
            fontWeight = FontWeight.SemiBold,
        )
    }
}