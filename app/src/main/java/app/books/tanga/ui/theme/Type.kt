package app.books.tanga.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.books.tanga.R

val Montserra = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold)
)

val Poppins = FontFamily(
    Font(R.font.poppins_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Montserra,
        fontWeight = FontWeight.SemiBold,
        fontSize = 38.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Montserra,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    )
)