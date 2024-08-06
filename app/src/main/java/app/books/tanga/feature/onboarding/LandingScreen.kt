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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.SystemBarsVisibility
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.components.TextWithShadow
import app.books.tanga.coreui.theme.Crayola
import app.books.tanga.coreui.theme.OxfordBlueTransp
import app.books.tanga.coreui.theme.TangaTheme

/**
 * Screen the user lands on when they open the app for the first time.
 * This screen triggers the onboarding flow.
 */
@Composable
fun LandingScreen(
    onNavigateToOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    SystemBarsVisibility(
        statusBarColor = Crayola,
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
                            OxfordBlueTransp,
                            MaterialTheme.colorScheme.onPrimaryContainer
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
                text = stringResource(id = R.string.onboarding_get_started),
                showInLightColor = true,
                onClick = onNavigateToOnboarding
            )
        }
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun LandingScreenPreview() {
    TangaTheme {
        LandingScreen(onNavigateToOnboarding = {})
    }
}
