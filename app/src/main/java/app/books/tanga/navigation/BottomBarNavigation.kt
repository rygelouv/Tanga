package app.books.tanga.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.books.tanga.R

enum class BottomNavScreen(
    val route: String,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int
) {
    HOME(
        route = NavigationScreen.Home.route,
        unselectedIcon = R.drawable.ic_bar_home_off,
        selectedIcon = R.drawable.ic_bar_home_on
    ),
    LIBRARY(
        route = NavigationScreen.Library.route,
        unselectedIcon = R.drawable.ic_bar_library_off,
        selectedIcon = R.drawable.ic_bar_library_on
    ),
    PROFILE(
        route = NavigationScreen.Profile.route,
        unselectedIcon = R.drawable.ic_bar_settings_off,
        selectedIcon = R.drawable.ic_bar_settings_on
    )
}

@Composable
fun BottomBarNavigation(navController: NavController) {
    val items = listOf(
        BottomNavScreen.HOME,
        BottomNavScreen.LIBRARY,
        BottomNavScreen.PROFILE,
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Unspecified
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon
                        ),
                        contentDescription = "bottom bar item"
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}