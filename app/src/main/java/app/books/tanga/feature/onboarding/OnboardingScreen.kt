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
import androidx.compose.runtime.remember
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
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.TangaButtonRightIcon
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.feature.auth.toAuthentication
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

const val MAX_PAGER_INDEX = 3

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    val pages =
        listOf(
            OnboardingPage.Read,
            OnboardingPage.Listen,
            OnboardingPage.Watch,
            OnboardingPage.Visualize
        )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val onNextClick: () -> Unit = remember {
        {
            scope.launch {
                if (pagerState.currentPage < MAX_PAGER_INDEX) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
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
            activeColor = MaterialTheme.colorScheme.secondary,
            inactiveColor = MaterialTheme.colorScheme.onTertiaryContainer,
            indicatorWidth = 10.dp
        )
        FinishOnboardingButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState,
            onNextClick = onNextClick,
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
            modifier = Modifier.align(Alignment.Center)
        ) {
            Button(
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.Center),
                onClick = onNextClick,
                colors =
                ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primary
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
            modifier = Modifier.fillMaxWidth()
        ) {
            TangaButtonRightIcon(
                onClick = onFinishClick,
                leftIcon = TangaIcons.RightArrow,
                text = stringResource(id = R.string.onboarding_get_started)
            )
        }
    }
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun OnboardingScreenPreview() {
    val navController = rememberNavController()
    TangaTheme {
        OnboardingScreen(navController)
    }
}
