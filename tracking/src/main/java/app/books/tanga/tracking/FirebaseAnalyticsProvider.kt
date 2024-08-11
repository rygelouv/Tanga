package app.books.tanga.tracking

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ParametersBuilder
import javax.inject.Inject

/**
 * Implementation of [AnalyticsProvider] for Firebase Analytics.
 */
class FirebaseAnalyticsProvider @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val parametersBuilder: ParametersBuilder
) : AnalyticsProvider {

    override fun track(event: Event, properties: Map<Property, Any>?) {
        val bundle = parametersBuilder.buildBundle(properties)

        firebaseAnalytics.logEvent(event.transformToFirebaseEvent(), bundle)
    }

    override fun trackPage(page: Page, properties: Map<Property, Any>?) {
        val bundle = parametersBuilder.buildBundle(properties)

        firebaseAnalytics.logEvent(page.pageName, bundle)
    }

    override fun setUserDetails(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun clearUserDetails() {
        firebaseAnalytics.setUserId(null)
    }

    private fun Event.transformToFirebaseEvent(): String = when (this) {
        Events.ACTION_USER_SIGNED_IN -> FirebaseAnalytics.Event.LOGIN
        Events.ACTION_SEARCH -> FirebaseAnalytics.Event.SEARCH
        Events.TAP_SHARE_SUMMARY -> FirebaseAnalytics.Event.SHARE
        Events.ACTION_SUBSCRIPTION_PURCHASED -> FirebaseAnalytics.Event.PURCHASE
        Events.TAP_SUMMARY -> FirebaseAnalytics.Event.SELECT_ITEM
        else -> this.eventName
    }
}

fun ParametersBuilder.buildBundle(properties: Map<Property, Any>?): Bundle {
    properties?.forEach { (property, value) ->
        when (value) {
            is String -> param(property.propertyName, value)
            is Int -> param(property.propertyName, value.toLong())
            is Long -> param(property.propertyName, value)
            is Double -> param(property.propertyName, value)
            else -> param(property.propertyName, value.toString())
        }
    }
    return bundle
}
