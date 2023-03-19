package app.books.tanga.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = TangaBluePale,
    primaryVariant = TangaOrange,
    secondary = Pink80
)

@Composable
fun TangaTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = TangaWhiteBackground
    )
    systemUiController.setNavigationBarColor(
        color = Color.White
    )
}