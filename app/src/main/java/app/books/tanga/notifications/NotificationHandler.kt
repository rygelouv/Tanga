package app.books.tanga.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import app.books.tanga.utils.BuildVersionChecker
import app.books.tanga.utils.ResourcesProvider
import javax.inject.Inject

/**
 * Interface that defines the contract for handling notifications
 */
interface NotificationHandler {

    /**
     * Takes the notification data received from Firebase and handle the display of the notification
     */
    fun handleNotification(notificationData: Map<String, String>)
}

class NotificationHandlerImpl @Inject constructor(
    private val notificationInfoFactory: NotificationInfoFactory,
    private val notificationBuilder: TangaNotificationBuilder,
    private val notificationManager: NotificationManager,
    private val resourcesProvider: ResourcesProvider
) : NotificationHandler {

    override fun handleNotification(notificationData: Map<String, String>) {
        // Create a TangaNotificationInfo object for remittance
        val notificationInfo = notificationInfoFactory.create(notificationData)
        // Check if Oreo or post-Oreo and create a channel if it doesn't exist already
        if (BuildVersionChecker.isAtLeastOreo() && notificationInfo.channel.exist().not()) {
            createChannel(notificationInfo.channel)
        }
        val notification = notificationBuilder.build(notificationInfo)

        notificationManager.notify(notificationInfo.notificationId, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(channel: TangaNotificationChannel) {
        val channelId = channel.name
        val channelName = resourcesProvider.getString(channel.channelNameResId)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
            setShowBadge(true)
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun TangaNotificationChannel.exist(): Boolean = notificationManager.getNotificationChannel(name) != null
}
