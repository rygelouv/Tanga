package app.books.tanga.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBarNavigation(navController: NavController) {
    val items = listOf(
        NavigationScreen.BottomBarScreen.Home,
        NavigationScreen.BottomBarScreen.Library,
        NavigationScreen.BottomBarScreen.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isBottomBarDestination = items.any { it.route == currentRoute }

    if (isBottomBarDestination) {
        NavigationBar(containerColor = Color.White) {
            items.forEach { item ->
                AddItem(
                    navController = navController,
                    currentRoute = currentRoute,
                    item = item
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    navController: NavController,
    currentRoute: String?,
    item: NavigationScreen.BottomBarScreen
) {
    NavigationBarItem(
        icon = {
            Icon(
                painter = painterResource(
                    id = if (currentRoute == item.route) item.selectedIcon
                    else item.unselectedIcon
                ),
                contentDescription = "bottom bar item",
                tint = if (currentRoute == item.route) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline
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
        alwaysShowLabel = false,
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.White
        )
    )
}