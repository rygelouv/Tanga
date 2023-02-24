package app.books.tanga.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.books.tanga.R
import app.books.tanga.navigation.NavigationScreen
import app.books.tanga.ui.theme.*
import com.google.accompanist.pager.*
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
            activeColor = TangaLightBlue,
            inactiveColor = TangaLightGray,
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
                navController.navigate(route = NavigationScreen.Authentication.route)
            }
        )
    }
}


@Composable
fun PagerScreen(onBoardingPage: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 28.dp),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = onBoardingPage.title,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                color = TextBlackMate,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 41.dp)
                    .padding(top = 20.dp)
                    .weight(1f),
                color = TextGray,
                text = onBoardingPage.description,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
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
                    backgroundColor = TangaBluePale,
                ),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "Next Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = pagerState.currentPage == MAX_PAGER_INDEX,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = modifier.size(64.dp),
                onClick = onFinishClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    backgroundColor = TangaBluePale,
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Text(text = "Get Started",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}