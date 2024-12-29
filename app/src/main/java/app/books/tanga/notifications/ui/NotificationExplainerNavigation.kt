package app.books.tanga.notifications.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.books.tanga.navigation.NavigationScreen
import app.books.tanga.notifications.NotificationPermissionTrigger

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.notificationExplainer(
    onNavigateToHomeScreen: () -> Unit,
    onClose: () -> Unit
) {
    composable(
        route = NavigationScreen.NotificationsExplainer.route,
        arguments = listOf(
            navArgument(NavigationScreen.NotificationsExplainer.TRIGGER_KEY) {
                type = NavType.StringType
            }
        )
    ) {
        val triggerValue = it.arguments?.getString(NavigationScreen.NotificationsExplainer.TRIGGER_KEY)
        val trigger = triggerValue?.let { value -> NotificationPermissionTrigger.valueOf(value) }
            ?: NotificationPermissionTrigger.SUMMARY_ACTION
        NotificationExplainerContainer(
            onNavigateToHomeScreen = onNavigateToHomeScreen,
            onClose = onClose,
            trigger = trigger
        )
    }
}

fun NavController.toNotificationExplainer(
    trigger: NotificationPermissionTrigger,
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(
        route = NavigationScreen.NotificationsExplainer.route.replace(
            oldValue = "{${NavigationScreen.NotificationsExplainer.TRIGGER_KEY}}",
            newValue = trigger.name
        )
    ) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}
