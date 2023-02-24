package app.books.tanga.navigation

sealed class NavigationScreen(val route: String) {
    object Onboarding: NavigationScreen("onboarding_screen")
    object Authentication: NavigationScreen("authentication_screen")
    object Main: NavigationScreen("main_screen")
    object Profile: NavigationScreen("profile_screen")
    object Home: NavigationScreen("home_screen")
    object Library: NavigationScreen("library_screen")
}
