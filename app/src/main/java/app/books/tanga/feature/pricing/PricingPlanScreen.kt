package app.books.tanga.feature.pricing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.DialogContent
import app.books.tanga.coreui.components.SystemBarsVisibility
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.resources.asString
import app.books.tanga.coreui.theme.LocalGradientColors
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme

@Composable
fun PricingPlanScreen(
    onCloseClick: () -> Unit,
    onPlanSelected: (PurchaseSubscriptionInput) -> Unit,
    state: PricingPlanUiState,
    modifier: Modifier = Modifier
) {
    SystemBarsVisibility()

    if (state.progressState == ProgressState.Show) {
        DialogContent()
    }

    val gradientColors =
        listOf(
            LocalGradientColors.current.end,
            LocalGradientColors.current.center,
            LocalGradientColors.current.center
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        PricingPlanTopBar(onCloseClick = onCloseClick)
        PricingPlanContent(state, onPlanSelected = onPlanSelected)
    }
}

@Composable
fun PricingPlanTopBar(modifier: Modifier = Modifier, onCloseClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(5f))
        Icon(
            painter = painterResource(id = TangaIcons.Close),
            contentDescription = "close icon",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier =
            Modifier
                .size(18.dp)
                .clickable { onCloseClick() }
        )
    }
}

@Composable
fun PricingPlanContent(
    state: PricingPlanUiState,
    onPlanSelected: (PurchaseSubscriptionInput) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .padding(horizontal = LocalSpacing.current.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        Image(
            modifier =
            Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 28.dp),
            painter = painterResource(id = R.drawable.graphic_pricing),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.large))

        Text(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium),
            text = stringResource(id = R.string.pricing_plan_message),
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))

        Offers()

        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))

        state.monthlyPlanUi ?: return
        state.yearlyPlanUi ?: return

        PricingPlans(
            monthlyPlan = state.monthlyPlanUi,
            yearlyPlan = state.yearlyPlanUi,
            onPlanSelected = { onPlanSelected(it) }
        )
        Text(
            modifier = Modifier.fillMaxWidth().clickable { },
            color = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(id = R.string.restore_purchase),
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun Offers() {
    Column(
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        PricingPlanOfferItem(stringResource(id = R.string.pricing_plan_offer_unlimited_access))
        PricingPlanOfferItem(stringResource(id = R.string.pricing_plan_offer_visual_graphic_summaries))
        PricingPlanOfferItem(stringResource(id = R.string.pricing_plan_offer_video_summaries))
        PricingPlanOfferItem(stringResource(id = R.string.pricing_plan_offer_offline_audio))
    }
}

@Composable
private fun PricingPlanOfferItem(text: String) {
    Row {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = TangaIcons.CheckMark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
        Text(
            text = text,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun PricingPlans(
    monthlyPlan: SubscriptionPlanUi,
    yearlyPlan: SubscriptionPlanUi,
    onPlanSelected: (PurchaseSubscriptionInput) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(
                start = LocalSpacing.current.small,
                end = LocalSpacing.current.small,
                bottom = LocalSpacing.current.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PricingPlanItem(
                modifier = Modifier.offset(y = 28.dp),
                title = yearlyPlan.title.asString(LocalContext.current.resources),
                price = yearlyPlan.price.asString(LocalContext.current.resources),
                cadence = yearlyPlan.cadence.asString(LocalContext.current.resources),
                selected = yearlyPlan.selected,
                highlight = true,
                onClick = { onPlanSelected(PurchaseSubscriptionInput(context, yearlyPlan)) }
            )
            BestValueLabel()
        }
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraMediumLarge))
        PricingPlanItem(
            title = monthlyPlan.title.asString(LocalContext.current.resources),
            price = monthlyPlan.price.asString(LocalContext.current.resources),
            cadence = monthlyPlan.cadence.asString(LocalContext.current.resources),
            selected = monthlyPlan.selected,
            highlight = false,
            onClick = { onPlanSelected(PurchaseSubscriptionInput(context, monthlyPlan)) }
        )
    }
}

@Composable
private fun BestValueLabel() {
    Box(
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .heightIn(min = 32.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = LocalSpacing.current.medium),
            text = stringResource(id = R.string.pricing_plan_best_value),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun PricingPlanScreenPreview() {
    TangaTheme {
        // PricingPlanScreen {}
    }
}
