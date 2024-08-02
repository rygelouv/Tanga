package app.books.tanga.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.SystemBarsVisibility
import app.books.tanga.coreui.components.TangaLinedButton
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.TangaTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val MAX_PAGER_INDEX = 3

private const val PAGER_WEIGHT = 5f

private const val SYSTEM_BAR_VISIBILITY_DELAY = 1000L

@Suppress("LongMethod")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToAuth: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var changeSystemVisibility by remember { mutableStateOf(false) }
    if (changeSystemVisibility) {
        SystemBarsVisibility(
            statusBarColor = MaterialTheme.colorScheme.primary,
            navigationBarColor = MaterialTheme.colorScheme.primary,
            statusBarVisible = true,
            navigationBarVisible = true
        )
    }

    LaunchedEffect(changeSystemVisibility) {
        delay(SYSTEM_BAR_VISIBILITY_DELAY)
        changeSystemVisibility = true
    }

    val pages =
        listOf(
            OnboardingPage.Read,
            OnboardingPage.Listen,
            OnboardingPage.Watch,
            OnboardingPage.Visualize
        )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HorizontalPager(
            modifier = Modifier.weight(PAGER_WEIGHT),
            count = 4,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) {
            PagerScreen(onBoardingPage = pages[it])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier =
            Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            activeColor = MaterialTheme.colorScheme.onSecondary,
            inactiveColor = MaterialTheme.colorScheme.tertiaryContainer,
            indicatorWidth = 8.dp
        )
        FinishOnboardingButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState,
            onNextClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage.inc())
                }
            },
            onFinishClick = {
                onOnboardingComplete()
                onNavigateBack()
                onNavigateToAuth()
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FinishOnboardingButton(
    pagerState: PagerState,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    onFinishClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = pagerState.currentPage != MAX_PAGER_INDEX,
            modifier = Modifier.align(Alignment.Center),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Button(
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.Center),
                onClick = onNextClick,
                colors =
                ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = TangaIcons.RightArrow),
                    contentDescription = "Next Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = pagerState.currentPage == MAX_PAGER_INDEX,
            modifier = Modifier.fillMaxWidth(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            TangaLinedButton(
                onClick = onFinishClick,
                text = stringResource(id = R.string.tanga_landing_get_started)
            )
        }
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun OnboardingScreenPreview() {
    TangaTheme {
        OnboardingScreen({}, {}, {})
    }
}
