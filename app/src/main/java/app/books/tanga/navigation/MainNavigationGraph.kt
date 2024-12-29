package app.books.tanga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.books.tanga.feature.audioplayer.fullplayer.playSummaryAudio
import app.books.tanga.feature.audioplayer.fullplayer.toPlaySummaryAudio
import app.books.tanga.feature.deleteaccount.deleteAccount
import app.books.tanga.feature.deleteaccount.toDeleteAccount
import app.books.tanga.feature.main.toMain
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
import app.books.tanga.notifications.NotificationPermissionTrigger
import app.books.tanga.notifications.ui.notificationExplainer
import app.books.tanga.notifications.ui.toNotificationExplainer

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
            onNavigateToRecommendedSummaryDetails = { summaryId -> navController.toSummaryDetails(summaryId) },
            onNavigateToPermissionExplainer = {
                navController.toNotificationExplainer(NotificationPermissionTrigger.SUMMARY_ACTION)
            }
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

        notificationExplainer(
            onNavigateToHomeScreen = { navController.toMain(screenToPopUpTo = NavigationScreen.Authentication) },
            onClose = { navController.popBackStack() }
        )
    }
}
