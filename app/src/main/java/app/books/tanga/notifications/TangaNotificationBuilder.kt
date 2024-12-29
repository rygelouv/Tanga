package app.books.tanga.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import app.books.tanga.R
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Interface that helps build Android [android.app.Notification] objects
 */
interface TangaNotificationBuilder {

    /**
     * Takes a [TangaNotificationInfo] object and builds an Android [android.app.Notification] object
     */
    fun build(notificationInfo: TangaNotificationInfo): Notification
}

class TangaNotificationBuilderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TangaNotificationBuilder {

    override fun build(notificationInfo: TangaNotificationInfo): Notification {
        val bitmap = runCatching {
            Glide.with(context)
                .asBitmap()
                .load(notificationInfo.coverImageUrl)
                .submit()
                .get()
        }.getOrNull()

        val builder = NotificationCompat.Builder(context, notificationInfo.channel.name)
            .setContentTitle(notificationInfo.title)
            .setContentText(notificationInfo.message)
            .setSmallIcon(R.drawable.ic_tanga_notification)
            .apply { bitmap?.let { setLargeIcon(it) } }
            .setColor(notificationInfo.color)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPublicVersion(buildPublicVersion(notificationInfo.channel.name)) // See [buildPublicVersion()]
            .setContentIntent(createPendingIntent(notificationInfo.deepLink))
        return builder.build()
    }

    /**
     * When screen is locked, the notification will be displayed as the public version
     */
    private fun buildPublicVersion(channelName: String): Notification {
        val publicTitle = context.getString(R.string.notification_default_title)
        val publicMessage = context.getString(R.string.notification_default_message)

        return NotificationCompat.Builder(context, channelName)
            .setContentTitle(publicTitle)
            .setContentText(publicMessage)
            .build()
    }

    /**
     * Create a [PendingIntent] using the provided deeplink
     */
    private fun createPendingIntent(deepLink: String): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        return PendingIntent.getActivity(
            context,
            /* Request code */
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
