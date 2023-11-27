package app.books.tanga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.books.tanga.feature.auth.authentication
import app.books.tanga.feature.auth.toAuthentication
import app.books.tanga.feature.main.mainScreen
import app.books.tanga.feature.main.toMain
import app.books.tanga.feature.onboarding.OnboardingScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: NavigationScreen
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(route = NavigationScreen.Onboarding.route) {
            OnboardingScreen(navController)
        }
        authentication(
            onAuthSuccess = { navController.toMain(screenToPopUpTo = NavigationScreen.Authentication) }
        )
        mainScreen {
            navController.toAuthentication(NavigationScreen.Main)
        }
    }
}
