package app.books.tanga.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.components.SystemBarsVisibility
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.components.TextWithShadow
import app.books.tanga.coreui.theme.TangaTheme

/**
 * Unused for now till we figure out proper edge to edge implementation and full display X-Y of background image
 */
@Composable
fun LandingScreen(
    onNavigateToOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    SystemBarsVisibility(
        statusBarColor = Color(0xFF9DB3B8),
        navigationBarColor = MaterialTheme.colorScheme.onPrimaryContainer,
        statusBarVisible = true
    )
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.reading_background_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x00001849),
                            Color(0xFF001849)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.padding(34.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            TextWithShadow(
                text = stringResource(id = R.string.tanga_landing_moto_variant_01)
            )

            Spacer(modifier = Modifier.height(64.dp))

            TangaButton(
                text = stringResource(id = R.string.tanga_landing_get_started),
                showInLightColor = true,
                onClick = onNavigateToOnboarding
            )
        }
    }
}

@Preview
@Composable
fun LandingScreenPreview() {
    TangaTheme {
        LandingScreen(onNavigateToOnboarding = {})
    }
}
