package app.books.tanga.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.books.tanga.navigation.BottomBarNavigation
import app.books.tanga.navigation.BottomBarNavigationGraph
import app.books.tanga.navigation.NavigationScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = rememberScaffoldState(),
        bottomBar = { BottomBarNavigation(navController) }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            BottomBarNavigationGraph(
                navController = navController,
                startDestination = NavigationScreen.Home.route
            )
        }
    }
}