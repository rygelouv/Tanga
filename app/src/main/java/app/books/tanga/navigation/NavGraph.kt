package app.books.tanga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.books.tanga.feature.auth.AuthScreen
import app.books.tanga.feature.home.HomeScreen
import app.books.tanga.feature.library.LibraryScreen
import app.books.tanga.feature.main.MainScreen
import app.books.tanga.feature.onboarding.OnboardingScreen
import app.books.tanga.feature.profile.ProfileScreen


@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: NavigationScreen
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(route = NavigationScreen.Onboarding.route) {
            OnboardingScreen(navController)
        }
        composable(route = NavigationScreen.Authentication.route) {
            AuthScreen(navController = navController)
        }
        composable(route = NavigationScreen.Main.route) {
            MainScreen(navController = navController)
        }
    }
}

@Composable
fun BottomBarNavigationGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NavigationScreen.Home.route) {
            HomeScreen()
        }
        composable(route = NavigationScreen.Library.route) {
            LibraryScreen()
        }
        composable(route = NavigationScreen.Profile.route) {
            ProfileScreen()
        }
    }
}