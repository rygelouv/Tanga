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
import app.books.tanga.notifications.NotificationPermissionTrigger
import app.books.tanga.notifications.ui.notificationExplainer
import app.books.tanga.notifications.ui.toNotificationExplainer
import app.books.tanga.utils.BuildVersionChecker

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
            onClose = {
                navController.popBackStack()
            },
            onNavigateToNotificationPermissionScreen = {
                navController.toNotificationExplainer(
                    trigger = NotificationPermissionTrigger.SKIP_AUTH
                )
            },
            onTermsAndPrivacyClick = { navController.toPrivacyAndTerms() }
        )

        if (BuildVersionChecker.isAtLeastTiramisu()) {
            notificationExplainer(
                onNavigateToHomeScreen = { navController.toMain(screenToPopUpTo = NavigationScreen.Authentication) },
                onClose = { navController.popBackStack() }
            )
        }

        mainScreen {
            navController.toAuthentication(NavigationScreen.Main, false)
        }
        privacyAndTerms(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
