package app.books.tanga.revenuecat

import app.books.tanga.entity.Price
import app.books.tanga.entity.SubscriptionPlan
import app.books.tanga.entity.SubscriptionType
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PackageType

fun Package.toSubscriptionPlan(): SubscriptionPlan = SubscriptionPlan(
    identifier = this.identifier,
    productId = this.product.id,
    type = this.packageType.toSubscriptionType(),
    price = Price(
        formattedValue = this.product.price.formatted,
        currency = this.product.price.currencyCode
    )
)

fun PackageType.toSubscriptionType(): SubscriptionType = when (this) {
    PackageType.MONTHLY -> SubscriptionType.MONTHLY
    PackageType.ANNUAL -> SubscriptionType.YEARLY
    else -> throw IllegalArgumentException("Unsupported package type: $this")
}
