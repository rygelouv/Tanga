package app.books.tanga.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.ShinyButton
import app.books.tanga.coreui.theme.TangaTheme

@Composable
fun ProButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    ShinyButton(
        modifier = modifier,
        text = R.string.profile_upgrade_to_pro,
        icon = R.drawable.ic_crown,
        onClick = onClick,
    )
}

@Preview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun ProButtonPreview() {
    TangaTheme {
        ProButton()
    }
}
