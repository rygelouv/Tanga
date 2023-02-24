package app.books.tanga.feature.onboarding

import androidx.annotation.DrawableRes
import app.books.tanga.R

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
