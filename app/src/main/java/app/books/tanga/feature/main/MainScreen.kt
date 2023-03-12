package app.books.tanga.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.books.tanga.navigation.BottomBarNavigation
import app.books.tanga.navigation.BottomBarNavigationGraph
import app.books.tanga.navigation.NavigationScreen

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = MainUiEvent.Empty)

    when(event) {
        is MainUiEvent.NavigateTo.ToAuth -> {
            navController.navigate(route = NavigationScreen.Authentication.route) {
                popUpTo(NavigationScreen.Authentication.route) { inclusive = true }
            }
        }
        else -> Unit
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = rememberScaffoldState(),
        bottomBar = { BottomBarNavigation(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            val bottomBarNavController = rememberNavController()
            BottomBarNavigationGraph(
                navController = bottomBarNavController,
                startDestination = NavigationScreen.Home.route
            )
        }
    }
}