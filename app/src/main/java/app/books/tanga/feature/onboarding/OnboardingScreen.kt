package app.books.tanga.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.books.tanga.R
import app.books.tanga.core_ui.R.drawable.ic_right_arrow
import app.books.tanga.core_ui.components.TangaButtonRightIcon
import app.books.tanga.feature.auth.toAuthentication
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

const val MAX_PAGER_INDEX = 3

@Preview
@Composable
fun OnboardingScreenPreview() {
    val navController = rememberNavController()
    OnboardingScreen(navController)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnboardingPage.Read,
        OnboardingPage.Listen,
        OnboardingPage.Watch,
        OnboardingPage.Visualize,
    )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = 4,
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) {
            PagerScreen(onBoardingPage = pages[it])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            activeColor = MaterialTheme.colorScheme.secondary,
            inactiveColor = MaterialTheme.colorScheme.onTertiaryContainer,
            indicatorWidth = 10.dp
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
                onboardingViewModel.onOnboardingCompleted()
                navController.popBackStack()
                navController.toAuthentication()
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FinishOnboardingButton(
    modifier: Modifier,
    pagerState: PagerState,
    onNextClick: () -> Unit,
    onFinishClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = pagerState.currentPage != MAX_PAGER_INDEX,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Button(
                modifier = modifier
                    .size(70.dp)
                    .align(Alignment.Center),
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = ic_right_arrow),
                    contentDescription = "Next Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = pagerState.currentPage == MAX_PAGER_INDEX,
            modifier = Modifier.fillMaxWidth()
        ) {
            TangaButtonRightIcon(
                onClick = onFinishClick,
                leftIcon = ic_right_arrow,
                text = stringResource(id = R.string.onboarding_get_started)
            )
        }
    }
}