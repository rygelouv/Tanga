package app.books.tanga

import android.content.Context
import app.books.tanga.entity.UserId
import app.books.tanga.errors.FirebaseCrashlyticsUserTracker
import app.books.tanga.errors.SentryTracker
import app.books.tanga.errors.TangaErrorTracker
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import java.util.Date
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TangaErrorTrackerTest {

    private val firebaseCrashlyticsUserTracker: FirebaseCrashlyticsUserTracker =
        mockk(relaxed = true)
    private val sentryTracker: SentryTracker = mockk(relaxed = true)
    private val context: Context = mockk()

    private lateinit var tangaErrorTracker: TangaErrorTracker

    @BeforeEach
    fun setup() {
        tangaErrorTracker =
            TangaErrorTracker(firebaseCrashlyticsUserTracker, sentryTracker, context)
    }

    @Test
    fun `init initializes the sentryTracker`() {
        // Given
        every { sentryTracker.init(context) } just Runs

        // When
        tangaErrorTracker.init()

        // Then
        verify { sentryTracker.init(context) }
    }

    @Test
    fun `setUserDetails sets user details on both trackers`() {
        // Given
        val userId = UserId("userId")
        val userCreationDate = Date()

        every { firebaseCrashlyticsUserTracker.setUserDetails(userId, userCreationDate) } just Runs
        every { sentryTracker.setUserDetails(userId, userCreationDate) } just Runs

        // When
        tangaErrorTracker.setUserDetails(userId, userCreationDate)

        // Then
        verify {
            firebaseCrashlyticsUserTracker.setUserDetails(userId, userCreationDate)
            sentryTracker.setUserDetails(userId, userCreationDate)
        }
    }

    @Test
    fun `clearUserDetails clears user details from both trackers`() {
        // Given
        every { firebaseCrashlyticsUserTracker.clearUserDetails() } just Runs
        every { sentryTracker.clearUserDetails() } just Runs

        // When
        tangaErrorTracker.clearUserDetails()

        // Then
        verify {
            firebaseCrashlyticsUserTracker.clearUserDetails()
            sentryTracker.clearUserDetails()
        }
    }
}
