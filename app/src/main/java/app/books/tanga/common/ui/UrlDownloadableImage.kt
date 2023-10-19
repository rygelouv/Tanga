package app.books.tanga.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.books.tanga.common.urls.StorageDownloadUrlGenerator
import app.books.tanga.coreui.components.GlideSummaryImage
import app.books.tanga.entity.SummaryId

/**
 * Composable function for displaying an image fetched from a URL and loaded using Glide.
 *
 * @param summaryId The ID of the book summary or item used to fetch the image.
 * @param modifier Modifier for the image composable.
 * @param onSummaryClicked A callback to be invoked when the summary is clicked.
 */
@Suppress("FunctionNaming")
@Composable
fun UrlDownloadableImage(
    summaryId: SummaryId,
    modifier: Modifier = Modifier,
    onSummaryClicked: (String) -> Unit
) {
    // A mutable state to hold the URL of the image to be downloaded and displayed.
    val imageUrlState = remember { mutableStateOf<String?>(null) }

    // Use LaunchedEffect to fetch the image URL when the summaryId changes.
    LaunchedEffect(summaryId) {
        // Generate the download URL for the cover image based on the summaryId.
        val url = StorageDownloadUrlGenerator.instance.generateCoverDownloadUrl(summaryId)
        imageUrlState.value = url
    }

    GlideSummaryImage(
        modifier = modifier,
        summaryId = summaryId.value,
        url = imageUrlState.value,
        onSummaryClicked = onSummaryClicked
    )
}
