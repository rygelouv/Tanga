package app.books.tanga.coreui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.coreui.theme.TangaTheme
import com.sendwave.remit.feature.support.chat.DotsAnimation

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    showSystemUi = true,
    device = "id:pixel_4a"
)
@Composable
private fun TypingIndicatorPreview() {
    TangaTheme {
        DotsAnimation()
    }
}
