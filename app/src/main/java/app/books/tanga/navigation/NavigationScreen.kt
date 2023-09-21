package app.books.tanga.navigation

import androidx.annotation.DrawableRes
import app.books.tanga.R

sealed class NavigationScreen(val route: String) {
    data object Onboarding : NavigationScreen("onboarding_screen")

    data object Authentication : NavigationScreen("authentication_screen")

    /**
     * Host the bottom bar and its screens + the screens launched from bottom bar screens
     */
    data object Main : NavigationScreen("main_screen")

    /**
     * Screens attached to Bottom bar
     */
    sealed class BottomBarScreen(
        route: String,
        @DrawableRes val unselectedIcon: Int,
        @DrawableRes val selectedIcon: Int
    ) : NavigationScreen(route) {
        data object Home : BottomBarScreen(
            route = "home_screen",
            unselectedIcon = R.drawable.ic_bar_home_off,
            selectedIcon = R.drawable.ic_bar_home_on
        )

        data object Library : BottomBarScreen(
            route = "library_screen",
            unselectedIcon = R.drawable.ic_bar_library_off,
            selectedIcon = R.drawable.ic_bar_library_on
        )

        data object Profile : BottomBarScreen(
            route = "profile_screen",
            unselectedIcon = R.drawable.ic_bar_settings_off,
            selectedIcon = R.drawable.ic_bar_settings_on
        )
    }

    data object SummaryDetails : NavigationScreen("summary_details_screen/summary_id={summaryId}") {
        const val SUMMARY_ID_KEY = "summaryId"
    }

    data object Search : NavigationScreen("search_screen")

    data object PlaySummaryAudio : NavigationScreen("play_summary_audio_screen")

    data object PricingPlan : NavigationScreen("pricing_plan_screen")
}
