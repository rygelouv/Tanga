package app.books.tanga.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.books.tanga.coreui.theme.LocalTintColor
import app.books.tanga.feature.home.home
import app.books.tanga.feature.library.library
import app.books.tanga.feature.profile.profile
import app.books.tanga.feature.profile.toPrivacyAndTerms
import app.books.tanga.feature.profile.toProfile
import app.books.tanga.feature.search.toSearch
import app.books.tanga.feature.settings.toSettings
import app.books.tanga.feature.subscription.toSubscription
import app.books.tanga.feature.summary.list.toSummariesByCategory
import app.books.tanga.feature.summary.toSummaryDetails

@Composable
fun BottomBarNavigation(navController: NavController) {
    val items =
        listOf(
            NavigationScreen.BottomBarScreen.Home,
            NavigationScreen.BottomBarScreen.Library,
            NavigationScreen.BottomBarScreen.Profile
        )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isBottomBarDestination = items.any { it.route == currentRoute }

    if (isBottomBarDestination) {
        NavigationBar(containerColor = Color.White, tonalElevation = 10.dp) {
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
    item: NavigationScreen.BottomBarScreen,
    modifier: Modifier = Modifier
) {
    NavigationBarItem(
        modifier = modifier.testTag(item.testTag),
        icon = {
            Icon(
                painter = painterResource(
                    id = if (currentRoute == item.route) {
                        item.selectedIcon
                    } else {
                        item.unselectedIcon
                    }
                ),
                contentDescription = item.route,
                tint = if (currentRoute == item.route) {
                    LocalTintColor.current.color
                } else {
                    MaterialTheme.colorScheme.onTertiaryContainer
                }
            )
        },
        selected = currentRoute == item.route,
        onClick = {
            navController.navigate(item.route) {
                navController.graph.startDestinationRoute?.let { screenRoute ->
                    popUpTo(screenRoute) {
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

fun NavGraphBuilder.bottomBarNavGraph(
    navController: NavHostController,
    onRedirectToAuth: () -> Unit
) {
    home(
        onNavigateToSearch = { navController.toSearch() },
        onNavigateToProfile = { navController.toProfile() },
        onNavigateToSummaryDetails = { summaryId -> navController.toSummaryDetails(summaryId) },
        onNavigateToSummariesByCategory = { categoryId, categoryName ->
            navController.toSummariesByCategory(categoryId, categoryName)
        }
    )
    library(
        onNavigateToSearch = { navController.toSearch() },
        onNavigateToSummaryDetails = { summaryId -> navController.toSummaryDetails(summaryId) }
    )
    profile(
        onProClicked = { navController.toSubscription() },
        onRedirectToAuth = onRedirectToAuth,
        onNavigateToSettings = { navController.toSettings() },
        onNavigateToPrivacyAndTerms = { navController.toPrivacyAndTerms() }
    )
}
