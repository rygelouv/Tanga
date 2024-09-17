package app.books.tanga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.books.tanga.feature.audioplayer.fullplayer.playSummaryAudio
import app.books.tanga.feature.audioplayer.fullplayer.toPlaySummaryAudio
import app.books.tanga.feature.deleteaccount.deleteAccount
import app.books.tanga.feature.deleteaccount.toDeleteAccount
import app.books.tanga.feature.profile.privacyAndTerms
import app.books.tanga.feature.read.readSummaryScreen
import app.books.tanga.feature.read.toReadSummaryScreen
import app.books.tanga.feature.search.search
import app.books.tanga.feature.search.toSearch
import app.books.tanga.feature.settings.settings
import app.books.tanga.feature.subscription.subscription
import app.books.tanga.feature.subscription.toSubscription
import app.books.tanga.feature.summary.list.summariesByCategory
import app.books.tanga.feature.summary.summaryDetails
import app.books.tanga.feature.summary.toSummaryDetails

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    startDestination: NavigationScreen.BottomBarScreen,
    onRedirectToAuth: () -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination.route) {
        bottomBarNavGraph(
            navController = navController,
            onRedirectToAuth = onRedirectToAuth
        )

        summaryDetails(
            onNavigateToAuth = onRedirectToAuth,
            onNavigateToSubscriptions = { navController.toSubscription() },
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToAudioPlayer = { summaryId -> navController.toPlaySummaryAudio(summaryId) },
            onNavigateToReadSummaryScreen = { summaryId -> navController.toReadSummaryScreen(summaryId.value) },
            onNavigateToRecommendedSummaryDetails = { summaryId -> navController.toSummaryDetails(summaryId) }
        )

        search(
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToSummary = { summaryId -> navController.toSummaryDetails(summaryId) }
        )

        playSummaryAudio { navController.popBackStack() }

        subscription(
            onCloseClicked = { navController.popBackStack() },
            onNavigateToAuth = onRedirectToAuth,
        )

        summariesByCategory(
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToSummary = { summaryId -> navController.toSummaryDetails(summaryId) },
            onNavigateToSearch = { navController.toSearch() }
        )

        readSummaryScreen(
            onNavigateToPreviousScreen = { navController.popBackStack() },
            onNavigateToAudioPlayer = { summaryId -> navController.toPlaySummaryAudio(summaryId) },
            onNavigateToPricingPlans = { navController.toSubscription() }
        )

        settings(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToAuth = onRedirectToAuth,
            onNavigateToDeleteAccount = { navController.toDeleteAccount() }
        )

        deleteAccount(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToAuth = onRedirectToAuth
        )

        privacyAndTerms(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
