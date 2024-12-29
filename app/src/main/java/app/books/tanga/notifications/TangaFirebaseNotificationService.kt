package app.books.tanga.notifications

import app.books.tanga.notifications.di.NotificationsCoroutineScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@AndroidEntryPoint
class TangaFirebaseNotificationService : FirebaseMessagingService() {

    @Inject
    @NotificationsCoroutineScope
    lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var notificationHandler: NotificationHandler

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("New Firebase Token: $token")

        // Subscribe to topics
        subscribeToTopics()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.d("Message received ==> data: ${remoteMessage.data}")

        // Handle message
        notificationHandler.handleNotification(remoteMessage.data)
    }

    @Suppress("TooGenericExceptionCaught")
    private fun subscribeToTopics() {
        coroutineScope.launch {
            try {
                listOf(Topics.NEW_SUMMARIES, Topics.WEEKLY_SUMMARY, Topics.SUBSCRIPTIONS)
                    .map { topic ->
                        async {
                            Firebase.messaging.subscribeToTopic(topic).await().let {
                                Timber.d("Subscribed to topic: $topic")
                            }
                        }
                    }.awaitAll()
            } catch (e: Exception) {
                Timber.e(e, "Unexpected error while subscribing to topics")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
