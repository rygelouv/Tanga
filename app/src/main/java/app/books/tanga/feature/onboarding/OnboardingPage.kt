package app.books.tanga.feature.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.core_ui.components.TangaDescriptionText

sealed class OnboardingPage(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {

    object Read : OnboardingPage(
        image = R.drawable.graphic_reading,
        title = "Read",
        description = "A maximum of 5 minutes read of book summaries"
    )

    object Listen: OnboardingPage(
        image = R.drawable.graphic_listening,
        title = "Listen",
        description = "You don't have time to read? Fine, you can listen to book summaries in 10 min"
    )

    object Watch: OnboardingPage(
        image = R.drawable.graphic_watching,
        title = "Watch",
        description = "The ultimate content format is video, get book summaries through books"
    )

    object Visualize: OnboardingPage(
        image = R.drawable.graphic_visualizing,
        title = "Visualize",
        description = "One image is worth a thousand words they. Summarize books through graphics",
    )
}


@Composable
fun PagerScreen(onBoardingPage: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 28.dp),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = onBoardingPage.title,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            TangaDescriptionText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 41.dp)
                    .padding(top = 20.dp)
                    .weight(1f),
                text = onBoardingPage.description,
            )
        }
    }
}
