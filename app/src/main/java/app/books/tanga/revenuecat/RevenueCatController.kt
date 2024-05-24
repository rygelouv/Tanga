package app.books.tanga.revenuecat

import android.content.Context
import app.books.tanga.BuildConfig
import app.books.tanga.entity.SubscriberInfo
import app.books.tanga.entity.SubscriptionPlan
import app.books.tanga.entity.SubscriptionType
import app.books.tanga.entity.UserId
import app.books.tanga.utils.findActivity
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.awaitCustomerInfo
import com.revenuecat.purchases.awaitOfferings
import com.revenuecat.purchases.awaitPurchase
import com.revenuecat.purchases.logInWith
import com.revenuecat.purchases.restorePurchasesWith
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

/**
 * Controller class that handles the RevenueCat SDK initialization, authentication, and purchase
 * and subscription operations.
 */
@Singleton
class RevenueCatController @Inject constructor() : RevenueCatInitializer, RevenueCatAuthenticator, RevenueCatPurchases {

    override fun initialize(context: Context) {
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(
            PurchasesConfiguration.Builder(
                context = context,
                apiKey = BuildConfig.REVENUECAT_API_KEY
            ).build()
        )
    }

    override fun logIn(userId: UserId) {
        Purchases.sharedInstance.logInWith(
            appUserID = userId.value,
            onSuccess = { _, _ -> Timber.d("Logged in with RevenueCat") },
            onError = { error ->
                Timber.e(
                    "Failed to log in with RevenueCat. " +
                        "Error code: ${error.code}, message: ${error.underlyingErrorMessage}"
                )
            }
        )
    }

    override fun logOut() {
        Purchases.sharedInstance.logOut()
    }

    /**
     * We only have two subscription plans: monthly and yearly.
     */
    override suspend fun purchase(params: RevenueCatPurchases.PurchaseParams): Result<Unit> {
        val offerings = Purchases.sharedInstance.awaitOfferings()
        val revenueCatPackage: Package? = when (params.subscriptionPlan.type) {
            SubscriptionType.MONTHLY -> offerings.current?.monthly
            SubscriptionType.YEARLY -> offerings.current?.annual
        }
        revenueCatPackage ?: return Result.failure(Exception("Failed to get package from RevenueCat"))
        return params.context.findActivity()?.let { activity ->
            runCatching {
                Purchases.sharedInstance.awaitPurchase(PurchaseParams.Builder(activity, revenueCatPackage).build())
                Unit
            }
        } ?: Result.failure(Exception("Failed to make a purchase with RevenueCat"))
    }

    /**
     * Get the customer info from RevenueCat and map it to our [SubscriberInfo] model.
     */
    override suspend fun getSubscriberInfo(): SubscriberInfo? = runCatching {
        val customerInfo = Purchases.sharedInstance.awaitCustomerInfo()
        SubscriberInfo(
            hasActiveSubscription = customerInfo.activeSubscriptions.isNotEmpty(),
            expirationDate = customerInfo.latestExpirationDate
        )
    }.getOrNull()

    override suspend fun getSubscriptions(): Result<List<SubscriptionPlan>> = runCatching {
        val offerings = Purchases.sharedInstance.awaitOfferings()
        listOfNotNull(
            offerings.current?.monthly?.also { Timber.d("\n\nMonthly: $it") },
            offerings.current?.annual?.also { Timber.d("\n\nAnnual: $it") }
        )
    }.map { offeringList ->
        offeringList.map { it.toSubscriptionPlan() }
    }

    override fun restorePurchase(): Boolean {
        var success = false
        Purchases.sharedInstance.restorePurchasesWith { customerInfo ->
            // check customerInfo to see if entitlement is now active
            if (customerInfo.entitlements.active.isNotEmpty()) {
                success = true
            }
        }
        return success
    }
}
