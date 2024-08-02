package app.books.tanga.feature.subscriptions

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.SubscriberInfo
import app.books.tanga.feature.subscription.PricingPlanUiEvent
import app.books.tanga.feature.subscription.PricingPlanUiState
import app.books.tanga.feature.subscription.PurchaseSubscriptionInput
import app.books.tanga.feature.subscription.SubscriptionViewModel
import app.books.tanga.feature.subscription.toUi
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.revenuecat.RevenueCatPurchases
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalTime
@ExperimentalCoroutinesApi
@ExtendWith(MainCoroutineDispatcherExtension::class)
@Disabled("Disabled for now, we will fix later")
class SubscriptionViewModelTest {

    private lateinit var viewModel: SubscriptionViewModel

    private val revenueCatController: RevenueCatPurchases = mockk()
    private val userRepository: UserRepository = mockk()

    @BeforeEach
    fun setUp() {
        viewModel = SubscriptionViewModel(revenueCatController, userRepository)
    }

    @Test
    fun `init should update state with subscription plans`() = runTest {
        val subscriptionPlans = listOf(
            Fixtures.subscriptionPlan1,
            Fixtures.subscriptionPlan2
        )
        coEvery { revenueCatController.getSubscriptions() } returns Result.success(subscriptionPlans)

        val initialState = viewModel.state.value
        assert(initialState == PricingPlanUiState())
        val updatedState = PricingPlanUiState(
            subscriptionPlans = subscriptionPlans,
            monthlyPlanUi = subscriptionPlans[0].toUi(),
            yearlyPlanUi = subscriptionPlans[1].toUi()
        )
        assert(viewModel.state.value == updatedState)

//        viewModel.state.test {
//            // assert(expectMostRecentItem() == PricingPlanUiState())
//            assert(
//                expectMostRecentItem() == PricingPlanUiState(
//                    subscriptionPlans = subscriptionPlans,
//                    monthlyPlanUi = subscriptionPlans[0].toUi(),
//                    yearlyPlanUi = subscriptionPlans[1].toUi()
//                )
//            )
//            cancelAndConsumeRemainingEvents()
//        }
    }

    @Test
    fun `onPlanSelected should update state and purchase successfully`() = runTest {
        val subscriptionPlan = Fixtures.subscriptionPlan1
        val subscriptionPlans = listOf(subscriptionPlan)
        val user = Fixtures.dummyUser
        val subscriberInfo = SubscriberInfo(true, Date())

        coEvery { revenueCatController.getSubscriptions() } returns Result.success(subscriptionPlans)
        coEvery { userRepository.getUser() } returns Result.success(user)
        coEvery { revenueCatController.purchase(any()) } returns Result.success(Unit)
        coEvery { revenueCatController.getSubscriberInfo() } returns subscriberInfo
        coEvery { userRepository.updateUser(any()) } returns Result.success(Unit)

        viewModel.onPlanSelected(PurchaseSubscriptionInput(mockk(), subscriptionPlan.toUi()))

        viewModel.state.test {
            assert(expectMostRecentItem() == PricingPlanUiState())
            assert(expectMostRecentItem().progressState == ProgressState.Show)
            assert(expectMostRecentItem().progressState == ProgressState.Hide)
            cancelAndConsumeRemainingEvents()
        }

        viewModel.events.test {
            assert(expectMostRecentItem() == PricingPlanUiEvent.SubscriptionPurchased)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onPlanSelected should handle purchase failure`() = runTest {
        val subscriptionPlan = Fixtures.subscriptionPlan1
        val subscriptionPlans = listOf(subscriptionPlan)

        coEvery { revenueCatController.getSubscriptions() } returns Result.success(subscriptionPlans)
        coEvery { revenueCatController.purchase(any()) } returns Result.failure(Exception("Purchase failed"))

        viewModel.onPlanSelected(PurchaseSubscriptionInput(mockk(), subscriptionPlan.toUi()))

        viewModel.state.test {
            assert(expectMostRecentItem() == PricingPlanUiState())
            assert(expectMostRecentItem().progressState == ProgressState.Show)
            assert(expectMostRecentItem().progressState == ProgressState.Hide)
            cancelAndConsumeRemainingEvents()
        }

        viewModel.events.test {
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }
}
