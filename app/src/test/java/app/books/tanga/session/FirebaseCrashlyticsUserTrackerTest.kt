package app.books.tanga.session

import app.books.tanga.entity.UserId
import app.books.tanga.errors.FirebaseCrashlyticsUserTracker
import app.books.tanga.utils.toFormattedString
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import java.util.Date
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

// See app.books.tanga.errors.FirebaseCrashlyticsUserTracker
private const val USER_CREATED_AT_KEY = "userCreationDate"

class FirebaseCrashlyticsUserTrackerTest {

    private lateinit var crashlytics: FirebaseCrashlytics
    private lateinit var firebaseCrashlyticsUserTracker: FirebaseCrashlyticsUserTracker

    @BeforeEach
    fun setup() {
        crashlytics = mockk(relaxed = true)
        firebaseCrashlyticsUserTracker = FirebaseCrashlyticsUserTracker(crashlytics)
    }

    @Test
    fun `verify userId and createdAt are set in Crashlytics`() {
        // Given
        val userId = UserId("sampleId")
        val createdAt: Date = mockk()
        mockkStatic("app.books.tanga.utils.ExtensionsKt")
        every { createdAt.toFormattedString() } returns "formattedDate"

        // When
        firebaseCrashlyticsUserTracker.setUserDetails(userId, createdAt)

        // Then
        verify {
            crashlytics.setUserId(userId.value)
            crashlytics.setCustomKey(USER_CREATED_AT_KEY, "formattedDate")
        }
    }

    @Test
    fun `verify user details are cleared from Crashlytics`() {
        // When
        firebaseCrashlyticsUserTracker.clearUserDetails()

        // Then
        verify {
            crashlytics.setUserId("")
            crashlytics.setCustomKey(USER_CREATED_AT_KEY, "")
        }
    }
}
