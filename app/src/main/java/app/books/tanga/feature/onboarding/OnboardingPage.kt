package app.books.tanga.feature.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.R.drawable
import app.books.tanga.coreui.components.TangaDescriptionText
import app.books.tanga.coreui.theme.LocalSpacing

sealed class OnboardingPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    data object Read : OnboardingPage(
        image = drawable.graphic_reading_glasses,
        title = R.string.onboarding_page_one_title,
        description = R.string.onboarding_page_one_description
    )

    data object Listen : OnboardingPage(
        image = R.drawable.graphic_listening,
        title = R.string.onboarding_page_two_title,
        description = R.string.onboarding_page_two_description
    )

    data object Watch : OnboardingPage(
        image = drawable.graphic_success_work_life,
        title = R.string.onboarding_page_three_title,
        description = R.string.onboarding_page_three_description
    )
}

@Composable
fun PagerScreen(
    onBoardingPage: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier =
            Modifier.weight(2f)
                .padding(horizontal = 48.dp),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager image"
        )
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(onBoardingPage.title),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
            TangaDescriptionText(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 41.dp)
                    .padding(top = 20.dp)
                    .weight(1f),
                text = stringResource(onBoardingPage.description),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
