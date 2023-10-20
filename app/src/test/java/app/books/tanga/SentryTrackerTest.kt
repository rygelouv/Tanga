package app.books.tanga

import app.books.tanga.entity.UserId
import app.books.tanga.errors.SentryTracker
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.verify
import io.sentry.Sentry
import io.sentry.android.core.SentryAndroid
import java.util.Date
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class SentryTrackerTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var sentryTracker: SentryTracker

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        sentryTracker = SentryTracker(testDispatcher)

        // Mock static calls for Sentry
        mockkStatic(SentryAndroid::class, Sentry::class)
        every { Sentry.setUser(any()) } just Runs
        every { Sentry.configureScope(any()) } just Runs
    }

    @Test
    fun `setUserDetails sets user details on Sentry`() = runTest {
        // Given
        val userId = UserId("testUserId")
        val userCreationDate = Date()

        // When
        sentryTracker.setUserDetails(userId, userCreationDate)

        // Then
        verify {
            Sentry.setUser(any())
            Sentry.configureScope(any())
        }
    }

    @Test
    fun `clearUserDetails clears user details from Sentry`() = runTest {
        // Given
        // (This stub is already provided above in setup())

        // When
        sentryTracker.clearUserDetails()

        // Then
        verify { Sentry.configureScope(any()) }
    }
}
