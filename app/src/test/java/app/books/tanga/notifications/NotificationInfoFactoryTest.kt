package app.books.tanga.notifications

import app.books.tanga.R
import app.books.tanga.entity.SummaryId
import app.books.tanga.utils.ResourcesProvider
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NotificationInfoFactoryTest {

    private val resourcesProvider: ResourcesProvider = mockk()
    private val notificationInfoFactory = NotificationInfoFactory(resourcesProvider)

    @Test
    fun `create should return TangaNotificationInfo with default values when notificationData is empty`() {
        // Arrange
        val notificationData = emptyMap<String, String>()
        every { resourcesProvider.getString(R.string.notifications_weekly_summary_title) } returns "Weekly Summary"
        every {
            resourcesProvider.getString(
                R.string.notifications_weekly_summary_message,
                "Default message",
                ""
            )
        } returns "Weekly Summary Message"
        every { resourcesProvider.getString(R.string.notification_default_message) } returns "Default message"
        every { resourcesProvider.getColor(R.color.tanga_blue) } returns 0x123456

        // Act
        val result = notificationInfoFactory.create(notificationData)

        // Assert
        assertEquals(TangaNotificationChannel.SUMMARIES, result.channel)
        assertEquals("Weekly Summary", result.title)
        assertEquals("Weekly Summary Message", result.message)
        assertEquals(0x123456, result.color)
        assertEquals(null, result.coverImageUrl)
        assertEquals("", result.deepLink)
    }

    @Test
    fun `create should use provided notification type to determine the channel`() {
        // Arrange
        val notificationData = mapOf(NotificationConstants.TYPE to "ANOTHER_CHANNEL")
        every { resourcesProvider.getString(R.string.notifications_weekly_summary_title) } returns "Weekly Summary"
        every {
            resourcesProvider.getString(
                R.string.notifications_weekly_summary_message,
                "Default message",
                ""
            )
        } returns "Weekly Summary Message"
        every { resourcesProvider.getString(R.string.notification_default_message) } returns "Default message"
        every { resourcesProvider.getColor(R.color.tanga_blue) } returns 0x123456

        // Act
        val result = notificationInfoFactory.create(notificationData)

        // Assert
        assertEquals(TangaNotificationChannel.fromName("ANOTHER_CHANNEL"), result.channel)
    }

    @Test
    fun `create should use notificationData values for message formatting`() {
        // Arrange
        val notificationData = mapOf(
            NotificationConstants.TITLE to "Custom Title",
            NotificationConstants.AUTHOR to "Author Name"
        )
        every { resourcesProvider.getString(R.string.notifications_weekly_summary_title) } returns "Weekly Summary"
        every {
            resourcesProvider.getString(
                R.string.notifications_weekly_summary_message,
                "Custom Title",
                "Author Name"
            )
        } returns "Weekly Summary Message"
        every { resourcesProvider.getColor(R.color.tanga_blue) } returns 0x123456

        // Act
        val result = notificationInfoFactory.create(notificationData)

        // Assert
        assertEquals("Weekly Summary Message", result.message)
    }

    @Test
    fun `create should set the correct summaryId from notificationData`() {
        // Arrange
        val notificationData = mapOf(
            NotificationConstants.SUMMARY_ID to "12345",
            NotificationConstants.TITLE to "Custom Title",
            NotificationConstants.AUTHOR to "Author Name",
            NotificationConstants.DEEP_LINK to "tanga://tanga.app/prefix"
        )
        every { resourcesProvider.getString(R.string.notifications_weekly_summary_title) } returns "Weekly Summary"
        every {
            resourcesProvider.getString(
                R.string.notifications_weekly_summary_message,
                any(),
                any()
            )
        } returns "Weekly Summary Message"
        every { resourcesProvider.getColor(R.color.tanga_blue) } returns 0x123456

        // Act
        val result = notificationInfoFactory.create(notificationData)

        // Assert
        assertEquals(SummaryId("12345"), result.summaryId)
        assertEquals("tanga://tanga.app/prefix", result.deepLink)
    }

    @Test
    fun `create should set default deepLink when not present in notificationData`() {
        // Arrange
        val notificationData = mapOf(
            NotificationConstants.TITLE to "Custom Title",
            NotificationConstants.AUTHOR to "Author Name"
        )
        every { resourcesProvider.getString(R.string.notifications_weekly_summary_title) } returns "Weekly Summary"
        every {
            resourcesProvider.getString(
                R.string.notifications_weekly_summary_message,
                any(),
                any()
            )
        } returns "Weekly Summary Message"
        every { resourcesProvider.getColor(R.color.tanga_blue) } returns 0x123456

        // Act
        val result = notificationInfoFactory.create(notificationData)

        // Assert
        assertEquals("", result.deepLink)
    }
}
