package app.books.tanga.coreui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import app.books.tanga.coreui.theme.Cerulean
import app.books.tanga.coreui.theme.Cultured
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * This composable is used to hide the status bar and the navigation bar.
 * */
@Composable
fun SystemBarsVisibility(
    statusBarColor: Color = Cerulean,
    navigationBarColor: Color? = null,
    statusBarVisible: Boolean = false,
    navigationBarVisible: Boolean = false
) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(key1 = true) {
        // Navigation bar id hidden
        systemUiController.isNavigationBarVisible = navigationBarVisible
        // Status bar is hidden
        systemUiController.isStatusBarVisible = statusBarVisible
        // Status bar color is changed to get the same color as the screen background
        systemUiController.setStatusBarColor(
            color = statusBarColor
        )
        navigationBarColor?.let {
            systemUiController.setNavigationBarColor(
                color = it
            )
        }

        onDispose {
            systemUiController.isNavigationBarVisible = true // Navigation bar is visible
            systemUiController.isStatusBarVisible = true // Status bar is visible
            // put back original status bar color
            systemUiController.setStatusBarColor(
                color = Cultured
            )
            systemUiController.setNavigationBarColor(
                color = Color.Unspecified
            )
        }
    }
}
