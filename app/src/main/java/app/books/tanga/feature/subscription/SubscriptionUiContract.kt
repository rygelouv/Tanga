package app.books.tanga.feature.subscription

import android.content.Context
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.resources.TextResource
import app.books.tanga.entity.SubscriptionPlan
import app.books.tanga.entity.SubscriptionType
import app.books.tanga.errors.UiError

data class PricingPlanUiState(
    val progressState: ProgressState = ProgressState.Hide,
    val subscriptionPlans: List<SubscriptionPlan>? = null,
    val monthlyPlanUi: SubscriptionPlanUi? = null,
    val yearlyPlanUi: SubscriptionPlanUi? = null,
    val error: UiError? = null
)

data class SubscriptionPlanUi(
    val productId: String,
    val title: TextResource,
    val price: TextResource,
    val cadence: TextResource,
    val selected: Boolean = false
)

data class PurchaseSubscriptionInput(
    val context: Context,
    val plan: SubscriptionPlanUi
)

fun SubscriptionPlan.toUi(selected: Boolean = false): SubscriptionPlanUi {
    val (title, price, cadence) = if (type == SubscriptionType.MONTHLY) {
        listOf(
            TextResource.fromStringId(R.string.pricing_plan_monthly),
            TextResource.fromText(price.formattedValue),
            TextResource.fromStringId(R.string.pricing_plan_monthly_cadence)
        )
    } else {
        listOf(
            TextResource.fromStringId(R.string.pricing_plan_yearly),
            TextResource.fromText(price.formattedValue),
            TextResource.fromStringId(R.string.pricing_plan_yearly_cadence)
        )
    }

    return SubscriptionPlanUi(
        productId = productId,
        title = title,
        price = price,
        cadence = cadence,
        selected = selected
    )
}

sealed interface PricingPlanUiEvent {
    data object Empty : PricingPlanUiEvent

    data class Error(
        val error: UiError
    ) : PricingPlanUiEvent

    data object SubscriptionPurchased : PricingPlanUiEvent
}
