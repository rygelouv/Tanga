package app.books.tanga.tracking

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ParametersBuilder
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirebaseAnalyticsProviderTest {

    private lateinit var firebaseAnalyticsProvider: FirebaseAnalyticsProvider
    private val firebaseAnalytics: FirebaseAnalytics = mockk(relaxed = true)
    private val parametersBuilder: ParametersBuilder = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        firebaseAnalyticsProvider = FirebaseAnalyticsProvider(firebaseAnalytics, parametersBuilder)
    }

    @Test
    fun trackEvent_logsEventWithProperties() {
        val event = Events.TAP_SEARCH
        val properties = mapOf<Property, Any>(Properties.SEARCH_QUERY to "atomic habits")
        val mockkBundle = mockk<Bundle>(relaxUnitFun = true)

        mockkStatic("app.books.tanga.tracking.FirebaseAnalyticsProviderKt")
        every { ParametersBuilder().buildBundle(properties) } returns mockkBundle

        firebaseAnalyticsProvider.track(event, properties)

        verify { firebaseAnalytics.logEvent(event.eventName, any()) }
    }

    @Test
    fun trackPage_logsPageWithProperties() {
        val page = Pages.SUMMARY_DETAILS
        val properties = mapOf<Property, Any>(Properties.SUMMARY_ID to "summary123")

        val mockkBundle = mockk<Bundle>(relaxUnitFun = true)

        mockkStatic("app.books.tanga.tracking.FirebaseAnalyticsProviderKt")
        every { ParametersBuilder().buildBundle(properties) } returns mockkBundle

        firebaseAnalyticsProvider.trackPage(page, properties)

        verify { firebaseAnalytics.logEvent(page.pageName, any()) }
    }

    @Test
    fun setUserDetails_setsUserId() {
        val userId = "user123"

        firebaseAnalyticsProvider.setUserDetails(userId)

        verify { firebaseAnalytics.setUserId(userId) }
    }

    @Test
    fun clearUserDetails_clearsUserId() {
        firebaseAnalyticsProvider.clearUserDetails()

        verify { firebaseAnalytics.setUserId(null) }
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic("app.books.tanga.tracking.FirebaseAnalyticsProviderKt")
    }
}
