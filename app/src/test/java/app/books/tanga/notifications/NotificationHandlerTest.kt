package app.books.tanga.notifications

import android.app.Notification
import android.app.NotificationManager
import app.books.tanga.utils.BuildVersionChecker
import app.books.tanga.utils.ResourcesProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NotificationHandlerImplTest {

    private val notificationInfoFactory: NotificationInfoFactory = mockk()
    private val notificationBuilder: TangaNotificationBuilder = mockk()
    private val notificationManager: NotificationManager = mockk(relaxed = true)
    private val resourcesProvider: ResourcesProvider = mockk()

    private lateinit var notificationHandler: NotificationHandlerImpl

    @BeforeEach
    fun setUp() {
        notificationHandler = NotificationHandlerImpl(
            notificationInfoFactory,
            notificationBuilder,
            notificationManager,
            resourcesProvider
        )
    }

    @Test
    fun `handleNotification should build and notify with a notification`() {
        // Arrange
        val notificationData = mapOf("key" to "value")
        val notificationInfo: TangaNotificationInfo = mockk(relaxed = true)
        val notification: Notification = mockk()

        every { notificationInfoFactory.create(notificationData) } returns notificationInfo
        every { notificationBuilder.build(notificationInfo) } returns notification

        // Act
        notificationHandler.handleNotification(notificationData)

        // Assert
        verify { notificationInfoFactory.create(notificationData) }
        verify { notificationBuilder.build(notificationInfo) }
        verify { notificationManager.notify(notificationInfo.notificationId, notification) }
    }

    @Test
    fun `handleNotification should create notification channel for Oreo or higher`() {
        // Arrange
        mockkObject(BuildVersionChecker)
        every { BuildVersionChecker.isAtLeastOreo() } returns true
        every { notificationManager.getNotificationChannel(any()) } returns null

        val notificationData = mapOf("key" to "value")
        val notificationInfo = TangaNotificationInfo(
            notificationId = 1,
            channel = TangaNotificationChannel.SUMMARIES,
            title = "Title",
            message = "Message",
            color = 0x123456,
            coverImageUrl = "https://example.com/image.png",
            deepLink = "https://example.com/deep-link"
        )
        val notification: Notification = mockk()
        val channelName = "Test Channel"

        every { notificationInfoFactory.create(notificationData) } returns notificationInfo
        every { notificationBuilder.build(notificationInfo) } returns notification
        every { resourcesProvider.getString(notificationInfo.channel.channelNameResId) } returns channelName

        // Act
        notificationHandler.handleNotification(notificationData)

        // Assert
        verify { notificationInfoFactory.create(notificationData) }
        // verify { resourcesProvider.getString(notificationInfo.channel.channelNameResId) }
        verify { notificationManager.createNotificationChannel(any()) }
        verify { notificationManager.notify(notificationInfo.notificationId, notification) }
    }

    @Test
    fun `handleNotification should not create notification channel if already exists`() {
        // Arrange
        mockkObject(BuildVersionChecker)
        every { BuildVersionChecker.isAtLeastOreo() } returns true
        every { notificationManager.getNotificationChannel(any()) } returns mockk()

        val notificationData = mapOf("key" to "value")
        val notificationInfo = TangaNotificationInfo(
            notificationId = 1,
            channel = TangaNotificationChannel.SUMMARIES,
            title = "Title",
            message = "Message",
            color = 0x123456,
            coverImageUrl = "https://example.com/image.png",
            deepLink = "https://example.com/deep-link"
        )
        val notification: Notification = mockk()

        every { notificationInfoFactory.create(notificationData) } returns notificationInfo
        every { notificationBuilder.build(notificationInfo) } returns notification

        // Act
        notificationHandler.handleNotification(notificationData)

        // Assert
        verify(exactly = 0) { notificationManager.createNotificationChannel(any()) }
        verify { notificationManager.notify(notificationInfo.notificationId, notification) }
    }
}
