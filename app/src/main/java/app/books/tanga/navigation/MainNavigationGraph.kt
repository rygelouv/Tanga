package app.books.tanga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.books.tanga.feature.home.HomeScreen
import app.books.tanga.feature.library.LibraryScreen
import app.books.tanga.feature.profile.ProfileScreen
import app.books.tanga.feature.profile.profile
import app.books.tanga.feature.profile.toProfile
import app.books.tanga.feature.search.search
import app.books.tanga.feature.search.toSearch
import app.books.tanga.feature.summary.details.SummaryDetailsScreen
import app.books.tanga.feature.summary.summaryDetails
import app.books.tanga.feature.summary.toSummaryDetails

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    startDestination: NavigationScreen.BottomBarScreen
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        bottomBarNavGraph(navController = navController)
        summaryDetails()
        search()
    }
}

fun NavGraphBuilder.bottomBarNavGraph(navController: NavHostController) {
    composable(route = NavigationScreen.BottomBarScreen.Home.route) {
        HomeScreen(
            onSearch = { navController.toSearch() },
            onProfilePictureClicked = { navController.toProfile() },
            onSummaryClicked = { navController.toSummaryDetails() }
        )
    }
    composable(route = NavigationScreen.BottomBarScreen.Library.route) {
        LibraryScreen(
            onExploreButtonClicked = { navController.toSearch() }
        )
    }
    profile()
}