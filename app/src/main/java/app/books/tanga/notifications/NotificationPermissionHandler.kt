package app.books.tanga.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.utils.BuildVersionChecker
import app.books.tanga.utils.ResourcesProvider
import javax.inject.Inject

interface NotificationPermissionHandler {

    suspend fun shouldRequestNotificationPermission(trigger: NotificationPermissionTrigger): Boolean
}

class NotificationPermissionHandlerImpl @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val dataStoreRepository: DefaultPrefDataStoreRepository
) : NotificationPermissionHandler {

    override suspend fun shouldRequestNotificationPermission(trigger: NotificationPermissionTrigger):
        Boolean = if (BuildVersionChecker.isAtLeastTiramisu()) {
        if (isPermissionGranted()) {
            false
        } else {
            when (trigger) {
                NotificationPermissionTrigger.SKIP_AUTH -> true
                NotificationPermissionTrigger.SUMMARY_ACTION -> handleTrigger()
            }
        }
    } else {
        false
    }

    private suspend fun handleTrigger(): Boolean {
        val counter = dataStoreRepository.getNotificationTriggerCounter() + 1
        dataStoreRepository.saveNotificationTriggerCounter(counter)
        return counter % 4 == 0
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isPermissionGranted(): Boolean =
        resourcesProvider.getPermissionStatus(Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
}

/**
 * Enum class that defines the triggers for requesting notification permission
 */
enum class NotificationPermissionTrigger {
    /**
     * Triggered when the user performs an action related to summary, such as reading or listening to a summary
     */
    SUMMARY_ACTION,

    /**
     * Triggered when the user skips the authentication process
     */
    SKIP_AUTH
}
