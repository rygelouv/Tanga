package app.books.tanga.coreui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes =
    Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(10.dp),
        extraLarge = RoundedCornerShape(12.dp)
    )

val Shapes.extraExtraLarge: RoundedCornerShape
    get() = RoundedCornerShape(16.dp)

val Shapes.extraExtraExtraLarge: RoundedCornerShape
    get() = RoundedCornerShape(22.dp)
