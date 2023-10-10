package app.books.tanga.feature.splash

import app.books.tanga.navigation.NavigationScreen

@Suppress("MatchingDeclarationName")
data class SplashState(
    val isLoading: Boolean = true,
    val startDestination: NavigationScreen? = null
)
