package app.books.tanga.coreui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.DotsAnimation
import app.books.tanga.coreui.theme.TangaTheme

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    showSystemUi = true,
    device = "id:pixel_4a"
)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TypingIndicatorPreview() {
    TangaTheme {
        DotsAnimation()
    }
}
