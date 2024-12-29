package app.books.tanga.notifications.ui

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import app.books.tanga.notifications.NotificationPermissionTrigger
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationExplainerContainer(
    onNavigateToHomeScreen: () -> Unit,
    onClose: () -> Unit,
    trigger: NotificationPermissionTrigger
) {
    val notificationPermissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    if (notificationPermissionState.status.isGranted) {
        if (trigger == NotificationPermissionTrigger.SKIP_AUTH) {
            onNavigateToHomeScreen()
        } else {
            onClose()
        }
    }

    NotificationExplainerScreen(
        onSkip = {
            if (trigger == NotificationPermissionTrigger.SKIP_AUTH) {
                onNavigateToHomeScreen()
            } else {
                onClose()
            }
        },
        onAllow = {
            notificationPermissionState.launchPermissionRequest()
        }
    )
}
