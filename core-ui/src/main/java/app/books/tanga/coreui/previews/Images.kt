package app.books.tanga.coreui.previews

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.R
import app.books.tanga.coreui.components.ProfileImage
import app.books.tanga.coreui.components.SummaryImage

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    showSystemUi = true,
    device = "id:pixel_4a"
)
@Composable
private fun SummaryImagePreview() {
    SummaryImage(
        summaryId = "1",
        modifier = Modifier.size(64.dp),
        painter = painterResource(id = R.drawable.profile_placeholder),
        onSummaryClick = {}
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun ProfilePicturePreview() {
    ProfileImage(
        modifier = Modifier.size(100.dp),
        photoUrl = "https://picsum.photos/200"
    )
}
