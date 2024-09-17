package app.books.tanga.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.books.tanga.feature.listen.MiniPlayerContainer
import app.books.tanga.feature.listen.toPlaySummaryAudio
import app.books.tanga.navigation.BottomBarNavigation
import app.books.tanga.navigation.MainNavigationGraph
import app.books.tanga.navigation.NavigationScreen

@Composable
fun MainScreen(
    onRedirectToAuth: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = MainUiEvent.Empty)

    when (event) {
        is MainUiEvent.NavigateTo.ToAuth -> {
            LaunchedEffect(Unit) {
                onRedirectToAuth()
            }
        }

        else -> Unit
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            Column {
                BottomBarNavigation(navController)
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.padding(it)) {
                MainNavigationGraph(
                    navController = navController,
                    startDestination = NavigationScreen.BottomBarScreen.Home,
                    onRedirectToAuth = onRedirectToAuth
                )
            }

            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            var routeState by remember { mutableStateOf("") }

            LaunchedEffect(currentBackStackEntry) {
                currentBackStackEntry?.destination?.route?.let { route ->
                    routeState = route
                }
            }

            if (routeState.isAudioPlayerDestination().not()) {
                MiniPlayerContainer(
                    currentDestinationRoute = routeState,
                    onExpendPlayer = { summaryId -> navController.toPlaySummaryAudio(summaryId) }
                )
            }
        }
    }
}

private fun String.isAudioPlayerDestination(): Boolean = NavigationScreen.PlaySummaryAudio.route.contains(this)
