package app.books.tanga.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import app.books.tanga.navigation.BottomBarNavigation
import app.books.tanga.navigation.MainNavigationGraph
import app.books.tanga.navigation.NavigationScreen

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = MainUiEvent.Empty)

    when (event) {
        is MainUiEvent.NavigateTo.ToAuth -> {
            LaunchedEffect(Unit) {
                onLogout()
            }
        }
        else -> Unit
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { BottomBarNavigation(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            MainNavigationGraph(
                navController = navController,
                startDestination = NavigationScreen.BottomBarScreen.Home
            )
        }
    }
}
