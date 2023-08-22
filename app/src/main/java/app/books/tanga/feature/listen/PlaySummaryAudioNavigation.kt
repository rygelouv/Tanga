package app.books.tanga.feature.listen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.books.tanga.navigation.NavigationScreen

fun NavGraphBuilder.playSummaryAudio(onBackClicked: () -> Unit) {
    composable(route = NavigationScreen.PlaySummaryAudio.route) {
        PlaySummaryAudioScreen(onBackClicked = onBackClicked)
    }
}

fun NavController.toPlaySummaryAudio(
    screenToPopUpTo: NavigationScreen? = null,
    isInclusive: Boolean = true
) {
    navigate(route = NavigationScreen.PlaySummaryAudio.route) {
        screenToPopUpTo?.let { popUpTo(it.route) { inclusive = isInclusive } }
    }
}