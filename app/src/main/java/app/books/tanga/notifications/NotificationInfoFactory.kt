package app.books.tanga.notifications

import app.books.tanga.R
import app.books.tanga.entity.SummaryId
import app.books.tanga.notifications.NotificationConstants.NOTIFICATION_ID_NUMBER_MAX
import app.books.tanga.notifications.NotificationConstants.NOTIFICATION_ID_NUMBER_MIN
import app.books.tanga.utils.ResourcesProvider
import javax.inject.Inject

/**
 * Factory class that creates [TangaNotificationInfo] objects
 */
class NotificationInfoFactory @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    /**
     * Takes the notification data received from Firebase and creates a [TangaNotificationInfo] object
     */
    fun create(notificationData: Map<String, String>): TangaNotificationInfo {
        // Generate an random notification ID number for each notification to be displayed.
        // We don't do notifications update for now. Updating notification will require more thinking on the backend.
        val notificationId = (NOTIFICATION_ID_NUMBER_MIN..NOTIFICATION_ID_NUMBER_MAX).random()

        // Determine the channel to use for the notification based on the notification type.
        // Default to SUMMARIES channel if not found.
        val channel = notificationData[NotificationConstants.TYPE]?.let { TangaNotificationChannel.fromName(it) }
            ?: TangaNotificationChannel.SUMMARIES

        val title = resourcesProvider.getString(R.string.notifications_weekly_summary_title)

        val message = resourcesProvider.getString(
            resourceId = R.string.notifications_weekly_summary_message,
            notificationData[NotificationConstants.TITLE]
                ?: resourcesProvider.getString(R.string.notification_default_message),
            notificationData[NotificationConstants.AUTHOR] ?: ""
        )

        val summaryId = SummaryId(notificationData[NotificationConstants.SUMMARY_ID] ?: "")

        return TangaNotificationInfo(
            notificationId = notificationId,
            summaryId = summaryId,
            title = title,
            message = message,
            color = resourcesProvider.getColor(R.color.tanga_blue),
            coverImageUrl = notificationData[NotificationConstants.COVER_IMAGE_URL],
            channel = channel,
            deepLink = notificationData[NotificationConstants.DEEP_LINK] ?: ""
        )
    }
}
