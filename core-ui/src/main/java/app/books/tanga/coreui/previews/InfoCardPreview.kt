package app.books.tanga.coreui.previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.R
import app.books.tanga.coreui.components.InfoCard
import app.books.tanga.coreui.theme.TangaTheme

@Preview(
    showSystemUi = true,
    device = "id:pixel_4a",
)
@Composable
fun InfoCardPreview() {
    TangaTheme {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoCard(
                image = R.drawable.graphic_career_simple
            )
        }
    }
}
