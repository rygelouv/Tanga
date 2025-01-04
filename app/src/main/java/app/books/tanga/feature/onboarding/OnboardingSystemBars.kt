package app.books.tanga.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import app.books.tanga.coreui.theme.Cultured
import app.books.tanga.coreui.theme.LandingStatusBar
import app.books.tanga.coreui.theme.Navy
import app.books.tanga.coreui.theme.YaleBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun OnboardingSystemBarsVisibility(
    transitionElement: OnboardingTransitionElement
) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(key1 = true) {
        systemUiController.setStatusBarColor(color = transitionElement.statusBarColor)
        systemUiController.setNavigationBarColor(color = transitionElement.navigationBarColor)

        onDispose {
            when (transitionElement) {
                is OnboardingTransitionElement.Landing -> Unit
                is OnboardingTransitionElement.Onboarding -> {
                    systemUiController.setStatusBarColor(
                        color = Cultured
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color.Unspecified
                    )
                }
            }
        }
    }
}

sealed class OnboardingTransitionElement(
    val statusBarColor: Color,
    val navigationBarColor: Color
) {
    data object Landing : OnboardingTransitionElement(
        statusBarColor = LandingStatusBar,
        navigationBarColor = Navy
    )
    data object Onboarding : OnboardingTransitionElement(
        statusBarColor = YaleBlue,
        navigationBarColor = YaleBlue
    )
}
