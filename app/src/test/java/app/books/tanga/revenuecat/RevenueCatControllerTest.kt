package app.books.tanga.revenuecat

import android.content.Context
import app.books.tanga.entity.SubscriptionPlan
import app.books.tanga.entity.UserId
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Offerings
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.awaitCustomerInfo
import com.revenuecat.purchases.awaitLogIn
import com.revenuecat.purchases.awaitOfferings
import com.revenuecat.purchases.awaitPurchase
import com.revenuecat.purchases.restorePurchasesWith
import dagger.Lazy
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineDispatcherExtension::class)
@Disabled(
    "The tests in this class are not working. There seem to be a mocking and/or coroutine issue." +
        "check https://github.com/RevenueCat/purchases-android/issues/173"
)
class RevenueCatControllerTest {

    private val purchases: Lazy<Purchases> = mockk()

    private lateinit var revenueCatController: RevenueCatController

    @BeforeEach
    fun setUp() {
        revenueCatController = RevenueCatController(purchases)
    }

//    @Test
//    fun `initialize should configure Purchases`() {
//        val context = mockk<Context>()
//        every { Purchases.configure(any<PurchasesConfiguration>()) } just Runs
//
//        revenueCatController.initialize(context)
//
//        verify { Purchases.configure(any<PurchasesConfiguration>()) }
//    }

    @Test
    fun `logIn should call logInWith on purchases`() = runTest {
        val userId = UserId("test_user")
        coEvery { purchases.get().awaitLogIn(userId.value) } returns mockk()

        revenueCatController.logIn(userId)

        coVerify { purchases.get().awaitLogIn(userId.value) }
    }

    @Test
    fun `logOut should call logOut on purchases`() {
        every { purchases.get().logOut() } just Runs

        revenueCatController.logOut()

        verify { purchases.get().logOut() }
    }

    @Test
    fun `purchase should handle purchase successfully`() = runTest {
        val context = mockk<Context>(relaxed = true)
        val subscriptionPlan = mockk<SubscriptionPlan>()
        val purchaseParams = RevenueCatPurchases.PurchaseParams(context, subscriptionPlan)
        val offerings = mockk<Offerings> {
            every { current?.monthly } returns mockk()
            every { current?.annual } returns null
        }
        coEvery { purchases.get().awaitOfferings() } returns offerings
        coEvery { purchases.get().awaitPurchase(any()) } returns mockk()

        val result = revenueCatController.purchase(purchaseParams)

        assert(result.isSuccess)
        coVerify { purchases.get().awaitPurchase(any()) }
    }

    @Test
    fun `getSubscriberInfo should return valid SubscriberInfo`() = runTest {
        val customerInfo = mockk<CustomerInfo> {
            every { activeSubscriptions } returns setOf("sub1")
            every { latestExpirationDate } returns mockk()
        }
        coEvery { purchases.get().awaitCustomerInfo() } returns customerInfo

        val subscriberInfo = revenueCatController.getSubscriberInfo()

        assert(subscriberInfo?.hasActiveSubscription == true)
    }

    @Test
    fun `getSubscriptions should return list of subscription plans`() = runTest {
        val monthlyPackage = mockk<Package>()
        val annualPackage = mockk<Package>()
        val offerings = mockk<Offerings> {
            every { current?.monthly } returns monthlyPackage
            every { current?.annual } returns annualPackage
        }
        coEvery { purchases.get().awaitOfferings() } returns offerings

        val result = revenueCatController.getSubscriptions()

        assert(result.isSuccess)
        assert(result.getOrNull()?.size == 2)
    }

    @Test
    fun `restorePurchase should return true if entitlements are active`() {
        val customerInfo = mockk<CustomerInfo> {
            every { entitlements.active } returns mapOf("entitlement" to mockk())
        }
        val userId = UserId("test_user")
        every { purchases.get().restorePurchasesWith(onError = any(), onSuccess = any()) } answers {
            firstArg<(CustomerInfo) -> Unit>().invoke(customerInfo)
        }

        val result = revenueCatController.restorePurchase()

        assert(result)
    }
}
