package app.books.tanga.navigation

import androidx.annotation.DrawableRes
import app.books.tanga.R

sealed class NavigationScreen(val route: String) {
    object Onboarding : NavigationScreen("onboarding_screen")

    object Authentication : NavigationScreen("authentication_screen")

    /**
     * Host the bottom bar and its screens + the screens launched from bottom bar screens
     */
    object Main : NavigationScreen("main_screen")

    /**
     * Screens attached to Bottom bar
     */
    sealed class BottomBarScreen(
        route: String,
        @DrawableRes val unselectedIcon: Int,
        @DrawableRes val selectedIcon: Int
    ) : NavigationScreen(route) {
        object Home : BottomBarScreen(
            route = "home_screen",
            unselectedIcon = R.drawable.ic_bar_home_off,
            selectedIcon = R.drawable.ic_bar_home_on
        )

        object Library : BottomBarScreen(
            route = "library_screen",
            unselectedIcon = R.drawable.ic_bar_library_off,
            selectedIcon = R.drawable.ic_bar_library_on
        )

        object Profile : BottomBarScreen(
            route = "profile_screen",
            unselectedIcon = R.drawable.ic_bar_settings_off,
            selectedIcon = R.drawable.ic_bar_settings_on
        )
    }

    object SummaryDetails : NavigationScreen("summary_details_screen")

    object Search : NavigationScreen("search_screen")
}
