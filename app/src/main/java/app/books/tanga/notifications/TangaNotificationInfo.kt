package app.books.tanga.notifications

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import app.books.tanga.R
import app.books.tanga.entity.SummaryId

/**
 * Represents the information needed to create an Android [android.app.Notification]
 *
 * @param notificationId: is the unique identifier for the notification
 * @param summaryId: is the unique identifier for the summary associated with the notification
 * @param title: is the title of the notification
 * @param message: is the message of the notification
 * @param smallIcon: is the icon that will be displayed in the notification
 * @param color: is the color that will be used to display the notification
 * @param channel: is the channel that the notification will be associated with
 * @param deepLink: is the deep link that will be opened when the notification is clicked
 */
data class TangaNotificationInfo(
    val notificationId: Int,
    val summaryId: SummaryId? = null,
    val title: String,
    val message: String,
    val coverImageUrl: String? = null,
    @ColorInt val color: Int,
    val channel: TangaNotificationChannel,
    val deepLink: String
)

/**
 * Represents the information needed to create and/or identity an Android [android.app.NotificationChannel]
 * The channel will be identified (channel ID) by the enum name: [TangaNotificationChannel.name]
 *
 * @param channelNameResId: is the human readable name of the channel that is displayed in the app notification settings
 */
enum class TangaNotificationChannel(@StringRes val channelNameResId: Int) {

    /**
     * Channel for general summary related notifications
     */
    SUMMARIES(R.string.notification_channel_summaries),

    /**
     * Channel for general subscription related notifications
     */
    SUBSCRIPTIONS(R.string.notification_channel_subscriptions);

    companion object {
        fun fromName(
            name: String
        ): TangaNotificationChannel = entries.find { it.name.lowercase() == name.lowercase() } ?: SUMMARIES
    }
}
