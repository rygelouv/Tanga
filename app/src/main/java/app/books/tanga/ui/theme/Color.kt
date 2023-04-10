package app.books.tanga.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


/**************************** Tanga Design System Colors ****************************/
// Color names are based on https://www.color-name.com/

// Blue Tones
val YaleBlue = Color(0xFF0F4C90)
val Cerulean = Color(0xFF1F91DD)
val Navy = Color(0xFF001849)
val BabyBlueEyes = Color(0xFFA8C8FF)
val AzureishWhite = Color(0xFFD6E3FF)
val Water = Color(0xFFCEE5FF)

// Gray Tones
val Cultured = Color(0xFFF6F3F6)
val Gray = Color(0xFF43474E)
val AuroMetalSaurus = Color(0xFF74777F)
val SilverFoil = Color(0xFFAFAFAF)

// Orange Tones
val Orange = Color(0xFFFA974A)
val OrangeTransparent = Color(0x33FA974A)

// Red Tones
val Red = Color(0xFFBA1A1A)
val DarkChocolate = Color(0xFF410002)
val PalePink = Color(0xFFFFDAD6)

// Standard Tones
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

/* Transparent Tones */
val NavyTransparent = Color(0x1A115A8E)

// Other Tones
/* Profile/Setting colors */
val ProfileYellow = Color(0xFFEFCC59)
val ProfileYellowBackground = Color(0xFFFCEFD0)
val ProfileGreen = Color(0xFF5AAF75)
val ProfileGreenBackground = Color(0xFFD4F4DC)
val ProfileRed = Color(0xFFD25F64)
val ProfileRedBackground = Color(0xFFFDD8D8)
val ProfilePurple = Color(0xFF5D62CD)
val ProfilePurpleBackground = Color(0xFFD7DAFE)

/********************************* End Tanga Design System Colors *********************************/

/********************************* Light color scheme *********************************/

val LightColors = lightColorScheme(
    primary = YaleBlue,
    onPrimary = White,
    primaryContainer = AzureishWhite,
    onPrimaryContainer = Navy,
    secondary = Cerulean,
    onSecondary = White,
    secondaryContainer = Water,
    onSecondaryContainer = Navy,
    tertiary = Orange,
    onTertiary = White,
    tertiaryContainer = OrangeTransparent,
    onTertiaryContainer = SilverFoil,
    error = Red,
    errorContainer = PalePink,
    onError = White,
    onErrorContainer = DarkChocolate,
    background = Cultured,
    onBackground = Navy,
    surface = Cultured,
    onSurface = Navy,
    surfaceVariant = Cultured,
    onSurfaceVariant = Gray,
    outline = AuroMetalSaurus,
    inverseOnSurface = Cultured,
    inverseSurface = YaleBlue,
    inversePrimary = BabyBlueEyes,
    surfaceTint = YaleBlue,
    outlineVariant = Cultured,
    scrim = Black,
)

/********************************* End light color scheme *********************************/

/********************************* Custom colors extension *********************************
 * Define additional colors that are not part of the color scheme.
 */
val ColorScheme.navyTransparent: Color
    get() = NavyTransparent

/********************************* End custom colors extension *********************************/

/********************************* Dark color (Unused for now) *********************************
 * This section should not be used for now. It is for future use when we have a dark theme.
 * This palette is still the one extracted from Material Theme Builder.
 */

val md_theme_dark_primary = Color(0xFFA8C8FF)
val md_theme_dark_onPrimary = Color(0xFF003062)
val md_theme_dark_primaryContainer = Color(0xFF00468A)
val md_theme_dark_onPrimaryContainer = Color(0xFFD6E3FF)
val md_theme_dark_secondary = Color(0xFF97CBFF)
val md_theme_dark_onSecondary = Color(0xFF003353)
val md_theme_dark_secondaryContainer = Color(0xFF004A76)
val md_theme_dark_onSecondaryContainer = Color(0xFFCEE5FF)
val md_theme_dark_tertiary = Color(0xFFFFB784)
val md_theme_dark_onTertiary = Color(0xFF4F2500)
val md_theme_dark_tertiaryContainer = Color(0xFF713700)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFDCC6)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF001849)
val md_theme_dark_onBackground = Color(0xFFDBE1FF)
val md_theme_dark_surface = Color(0xFF001849)
val md_theme_dark_onSurface = Color(0xFFDBE1FF)
val md_theme_dark_surfaceVariant = Color(0xFF43474E)
val md_theme_dark_onSurfaceVariant = Color(0xFFC4C6CF)
val md_theme_dark_outline = Color(0xFF8E9099)
val md_theme_dark_inverseOnSurface = Color(0xFF001849)
val md_theme_dark_inverseSurface = Color(0xFFDBE1FF)
val md_theme_dark_inversePrimary = Color(0xFF0F4C90)
val md_theme_dark_surfaceTint = Color(0xFFA8C8FF)
val md_theme_dark_outlineVariant = Color(0xFF43474E)
val md_theme_dark_scrim = Color(0xFF000000)

@Suppress("UnusedPrivateMember")
private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

/********************************* End Dark color *********************************/