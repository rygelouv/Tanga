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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.Cerulean
import app.books.tanga.coreui.theme.Cultured
import app.books.tanga.coreui.theme.LocalGradientColors
import app.books.tanga.coreui.theme.LocalSpacing
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PricingPlanScreen(onCloseClicked: () -> Unit) {
    SystemBarsVisibility()

    val gradientColors =
        listOf(
            LocalGradientColors.current.end,
            LocalGradientColors.current.center,
            LocalGradientColors.current.center
        )

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors))
            .verticalScroll(
                rememberScrollState(),
            )
    ) {
        PricingPlanTopBar(onCloseClicked)
        PricingPlanContent()
    }
}

/**
 * This composable is used to hide the status bar and the navigation bar.
 * */
@Composable
private fun SystemBarsVisibility() {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(key1 = true) {
        // Navigation bar id hidden
        systemUiController.isNavigationBarVisible = false
        // Status bar color is changed to get the same color as the screen background
        systemUiController.setStatusBarColor(
            color = Cerulean,
        )
        onDispose {
            systemUiController.isNavigationBarVisible = true // Navigation bar is visible
            // put back original status bar color
            systemUiController.setStatusBarColor(
                color = Cultured,
            )
        }
    }
}

@Composable
fun PricingPlanTopBar(onCloseClicked: () -> Unit) {
    Row(
        modifier =
        Modifier
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
                .clickable { onCloseClicked() }
        )
    }
}

@Composable
fun PricingPlanContent() {
    Column(
        modifier =
        Modifier
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

        PricingPlans()
    }
}

@Composable
private fun Offers() {
    Column(
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        PricingPlanOfferItem(stringResource(id = R.string.pricing_plan_offer_no_ads))
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
fun PricingPlans() {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(
                start = LocalSpacing.current.small,
                end = LocalSpacing.current.small,
                bottom = LocalSpacing.current.medium,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            PricingPlanItem(
                modifier = Modifier.offset(y = 28.dp),
                title = stringResource(id = R.string.pricing_plan_yearly),
                price = stringResource(id = R.string.pricing_plan_yearly_price),
                cadence = stringResource(id = R.string.pricing_plan_yearly_cadence),
                onClick = { /*TODO*/ }
            )
            BestValueLabel()
        }
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraMediumLarge))
        PricingPlanItem(
            title = stringResource(id = R.string.pricing_plan_monthly),
            price = stringResource(id = R.string.pricing_plan_monthly_price),
            cadence = stringResource(id = R.string.pricing_plan_monthly_cadence),
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
private fun BestValueLabel() {
    Box(
        modifier =
        Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(32.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium,
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier =
            Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = LocalSpacing.current.medium),
            text = stringResource(id = R.string.pricing_plan_best_value),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}
