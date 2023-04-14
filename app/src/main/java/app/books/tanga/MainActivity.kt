package app.books.tanga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import app.books.tanga.feature.splash.SplashViewModel
import app.books.tanga.navigation.NavigationGraph
import app.books.tanga.core_ui.theme.TangaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.state.value.isLoading
        }

        setContent {
            TangaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by splashViewModel.state
                    state.startDestination?.let { destination ->
                        val navController = rememberNavController()
                        NavigationGraph(
                            navController = navController,
                            startDestination = destination
                        )
                    }
                }
            }
        }
    }
}