package app.books.tanga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.books.tanga.feature.home.HomeScreen
import app.books.tanga.feature.library.LibraryScreen
import app.books.tanga.feature.listen.playSummaryAudio
import app.books.tanga.feature.listen.toPlaySummaryAudio
import app.books.tanga.feature.pricing.pricingPlan
import app.books.tanga.feature.pricing.toPricingPlan
import app.books.tanga.feature.profile.profile
import app.books.tanga.feature.profile.toProfile
import app.books.tanga.feature.search.search
import app.books.tanga.feature.search.toSearch
import app.books.tanga.feature.summary.summaryDetails
import app.books.tanga.feature.summary.toSummaryDetails

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    startDestination: NavigationScreen.BottomBarScreen,
    onRedirectToAuth: () -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        bottomBarNavGraph(navController = navController, onRedirectToAuth = onRedirectToAuth)
        summaryDetails(
            onNavigateToAuth = onRedirectToAuth,
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToAudioPlayer = { summaryId -> navController.toPlaySummaryAudio(summaryId.value) },
            onNavigateToRecommendedSummaryDetails = { summaryId -> navController.toSummaryDetails(summaryId.value) }
        )
        search(
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToSummary = { summaryId -> navController.toSummaryDetails(summaryId.value) }
        )
        playSummaryAudio { navController.popBackStack() }
        pricingPlan { navController.popBackStack() }
    }
}

fun NavGraphBuilder.bottomBarNavGraph(navController: NavHostController, onRedirectToAuth: () -> Unit) {
    composable(route = NavigationScreen.BottomBarScreen.Home.route) {
        HomeScreen(
            onSearch = { navController.toSearch() },
            onProfilePictureClick = { navController.toProfile() },
            onSummaryClick = { summaryId -> navController.toSummaryDetails(summaryId) }
        )
    }
    composable(route = NavigationScreen.BottomBarScreen.Library.route) {
        LibraryScreen(
            onExploreButtonClick = { navController.toSearch() },
            onFavoriteClick = { summaryId -> navController.toSummaryDetails(summaryId) }
        )
    }
    profile(
        onProClicked = { navController.toPricingPlan() },
        onRedirectToAuth = onRedirectToAuth
    )
}
