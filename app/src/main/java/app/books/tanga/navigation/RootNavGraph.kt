package app.books.tanga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.books.tanga.feature.auth.authentication
import app.books.tanga.feature.auth.toAuthentication
import app.books.tanga.feature.main.mainScreen
import app.books.tanga.feature.main.toMain
import app.books.tanga.feature.onboarding.landing
import app.books.tanga.feature.onboarding.onboarding
import app.books.tanga.feature.onboarding.toOnboarding
import app.books.tanga.feature.profile.privacyAndTerms
import app.books.tanga.feature.profile.toPrivacyAndTerms

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: NavigationScreen
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        landing(
            onNavigateToOnboarding = {
                navController.toOnboarding(NavigationScreen.Landing)
            }
        )
        onboarding(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToAuth = { navController.toAuthentication(NavigationScreen.Onboarding) }
        )
        authentication(
            onAuthSuccess = { navController.toMain(screenToPopUpTo = NavigationScreen.Authentication) },
            onTermsAndPrivacyClick = { navController.toPrivacyAndTerms() }
        )
        mainScreen {
            navController.toAuthentication(NavigationScreen.Main)
        }

        privacyAndTerms(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
