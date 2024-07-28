package app.books.tanga.coreui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.resources.TextResource
import app.books.tanga.coreui.resources.asString
import app.books.tanga.coreui.theme.LocalSpacing

/**
 * Action content is a component that is used to display a UI screen content with an image, title,
 * description and two buttons. The main button is always displayed. The secondary button is optional.
 */
@Composable
fun ActionContent(
    data: ActionData,
    modifier: Modifier = Modifier,
    titleTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    bigSpacingBeforeButtons: Boolean = false,
    shouldCenterDescriptionText: Boolean = true,
    shouldShowWhiteBackground: Boolean = true
) {
    val contentModifier = if (shouldShowWhiteBackground) {
        modifier.background(Color.White)
    } else {
        modifier
    }

    Column(
        modifier = contentModifier
            .padding(
                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.small
            ).verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current
        Image(
            modifier = Modifier
                .size(230.dp)
                .padding(horizontal = 10.dp),
            painter = painterResource(id = data.icon),
            contentDescription = "action content image"
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.large))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = data.title.asString(context.resources),
            style = MaterialTheme.typography.titleLarge,
            color = titleTextColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            text = data.description.asString(context.resources),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            textAlign = if (shouldCenterDescriptionText) TextAlign.Center else TextAlign.Start
        )
        if (bigSpacingBeforeButtons) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))
        }

        // Minimum spacing to guarantee that the buttons are not too close to description text
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        TangaButton(
            text = data.mainButton.text.asString(context.resources),
            onClick = data.mainButton.onClick
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.small))
        data.secondaryButton?.let {
            TangaLinedButton(
                text = it.text.asString(context.resources),
                onClick = it.onClick
            )
        }
    }
}

/**
 * Represents the data needed to display an action content.
 */
data class ActionData(
    val title: TextResource,
    val description: TextResource,
    @DrawableRes val icon: Int,
    val mainButton: Button,
    val secondaryButton: Button? = null
)
