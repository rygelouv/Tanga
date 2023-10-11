package app.books.tanga.coreui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TangaTheme(content: @Composable () -> Unit) {
    val colors = LightColors
    val gradientColors =
        GradientColors(
            start = colors.onPrimaryContainer,
            center = colors.primary,
            end = colors.secondary
        )
    val tintColor =
        TintColor(
            color = colors.primary,
            disabled = colors.onSurfaceVariant.copy(alpha = 0.38f)
        )
    val spacing = Spacing()

    CompositionLocalProvider(
        LocalTintColor provides tintColor,
        LocalGradientColors provides gradientColors,
        LocalSpacing provides spacing
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Cultured
    )
    systemUiController.setNavigationBarColor(
        color = Color.White
    )
}
