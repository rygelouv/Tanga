package app.books.tanga.tracking

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AnalyticsTrackerImplTest {

    private lateinit var analyticsTracker: AnalyticsTrackerImpl
    private val provider1: AnalyticsProvider = mockk(relaxed = true)
    private val provider2: AnalyticsProvider = mockk(relaxed = true)
    private val providers = listOf(provider1, provider2)

    @BeforeEach
    fun setUp() {
        analyticsTracker = AnalyticsTrackerImpl(providers)
    }

    @Test
    fun trackEvent_callsAllProviders() {
        val event = mockk<Event>()
        val properties = mapOf<Property, Any>(mockk<Property>() to "value")

        analyticsTracker.track(event, properties)

        verify { provider1.track(event, properties) }
        verify { provider2.track(event, properties) }
    }

    @Test
    fun trackPage_callsAllProviders() {
        val page = mockk<Page>()
        val properties = mapOf<Property, Any>(mockk<Property>() to "value")

        analyticsTracker.trackPage(page, properties)

        verify { provider1.trackPage(page, properties) }
        verify { provider2.trackPage(page, properties) }
    }

    @Test
    fun setUserDetails_callsAllProviders() {
        val userId = "user123"

        analyticsTracker.setUserDetails(userId)

        verify { provider1.setUserDetails(userId) }
        verify { provider2.setUserDetails(userId) }
    }

    @Test
    fun clearUserDetails_callsAllProviders() {
        analyticsTracker.clearUserDetails()

        verify { provider1.clearUserDetails() }
        verify { provider2.clearUserDetails() }
    }
}
