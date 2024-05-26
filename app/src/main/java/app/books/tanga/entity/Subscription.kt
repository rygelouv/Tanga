package app.books.tanga.entity

import java.util.Date

/**
 * Represents a subscription plan that a user can purchase.
 * @property identifier A unique identifier for the subscription plan.
 * @property productId The Store product ID of the subscription plan.
 * @property type The type of the subscription plan (monthly or yearly).
 * @property price The price of the subscription plan.
 */
data class SubscriptionPlan(
    val identifier: String,
    val productId: String,
    val type: SubscriptionType,
    val price: Price,
)

/**
 * Represents the type of a subscription plan.
 */
enum class SubscriptionType {
    MONTHLY,
    YEARLY
}

/**
 * Represents the price of a subscription plan.
 * @property formattedValue The formatted value of the price.
 * @property currency The currency in which the price is displayed.
 */
data class Price(
    val formattedValue: String,
    val currency: String,
)

data class SubscriberInfo(
    val hasActiveSubscription: Boolean,
    val expirationDate: Date?,
)
