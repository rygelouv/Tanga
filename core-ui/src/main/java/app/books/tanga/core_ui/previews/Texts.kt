package app.books.tanga.core_ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import app.books.tanga.core_ui.components.TangaDescriptionText

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TangaDescriptionTexPreviewLong() {
    TangaDescriptionText(
        text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed euismod, nunc sit amet ultricies lacinia, nisl nisl lacinia nisl, " +
                "nec aliquam nisl nisl eu nisl. Sed euismod, nunc sit amet ultricies " +
                "lacinia, nisl nisl lacinia nisl, nec aliquam nisl nisl eu nisl. " +
                "Sed euismod, nunc sit amet ultricies lacinia, nisl nisl lacinia nisl, " +
                "nec aliquam nisl nisl eu nisl. Sed euismod, nunc sit amet ultricies lacinia," +
                " nisl nisl lacinia nisl, nec aliquam nisl nisl eu nisl. Sed euismod, " +
                "nunc sit amet ultricies lacinia, nisl nisl lacinia nisl, nec aliquam " +
                "nisl nisl eu nisl. Sed euismod, nunc sit amet ultricies lacinia, nisl " +
                "nisl lacinia nisl, nec aliquam nisl nisl eu nisl. Sed euismod, nunc sit " +
                "amet ultricies lacinia, nisl nisl lacinia nisl, nec aliquam nisl nisl " +
                "eu nisl. Sed euismod, nunc sit amet ultricies lacinia, nisl nisl lacinia nisl, " +
                "nec aliquam nisl nisl eu nisl. Sed euismod, nunc sit amet ultricies lacinia, " +
                "nisl nisl lacinia nisl, nec aliquam nisl nisl eu nisl. Sed euismod, " +
                "nunc sit amet ultricies lacinia, nisl nisl lacinia nisl, nec aliquam nisl " +
                "nisl eu nisl. Sed euismod, nunc sit amet ultricies lacinia, nisl nisl lacinia" +
                " nisl, nec aliquam nisl nisl eu nisl. Sed euismod, nunc sit amet ultricies " +
                "lacinia, nisl nisl lacinia nisl, nec aliquam nisl nisl eu nisl. Sed euismod," +
                " nunc sit amet ultricies lacinia, nisl nisl lacinia nisl, nec aliquam nisl nisl " +
                "eu nisl. Sed euismod, nunc sit amet ultricies",
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    showSystemUi = true,
    device = "id:pixel_4a",
)
@Composable
fun TangaDescriptionTexPreview() {
    TangaDescriptionText(
        text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed euismod, nunc sit amet ultricies lacinia, nisl nisl lacinia nisl, " +
                "nec aliquam nisl nisl eu nisl. Sed euismod, nunc sit amet ultricies " +
                "lacinia, nisl nisl lacinia nisl",
        textAlign = TextAlign.Start,
        maxLines = 3,
    )
}
