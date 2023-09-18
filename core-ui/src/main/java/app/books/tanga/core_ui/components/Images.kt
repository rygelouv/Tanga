package app.books.tanga.core_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.books.tanga.core_ui.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


/**
 * This is a composable that displays an image of a book summary cover.
 *
 * @param modifier: An instance of [Modifier] that can be used to apply styling to the [Surface] and [Image] composable.
 * @param painter: The [Painter] object that represents the image to be displayed.
 * @param onSummaryClicked: The function to be executed when the image is clicked.
 * */
@Composable
fun SummaryImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    onSummaryClicked: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 10.dp)
            .clickable { onSummaryClicked() },
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painter,
            contentDescription = "summary cover",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/**
 * This is a composable that displays an image of a book summary cover.
 * It uses Glide to load the image from a URL. If the URL is null, it uses a placeholder image.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideSummaryImage(
    modifier: Modifier = Modifier,
    model: String? = null,
    painter: Painter? = null,
    onSummaryClicked: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 10.dp)
            .clickable { onSummaryClicked() },
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        if (model != null) {
            GlideImage(
                model = model,
                contentDescription = "summary cover",
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            // TODO we need to remove this condition when we have got rid of FakeData usage
            Image(
                painter = painter!!,
                contentDescription = "summary cover",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}


/**
 * This is a composable function that displays a profile image with optional border and on-click listener.
 *
 * @param modifier: An instance of [Modifier] that can be used to apply styling to the [Surface] and [Image] composable.
 * @param tag: A tag that can be used for testing purposes.
 * @param photoUrl: The URL of the image to be displayed, or null if a placeholder image should be used.
 * @param shape: The shape of the [Surface] composable that surrounds the image.
 * @param size: The size of the [Surface] composable that surrounds the image.
 * @param hasBorder: Whether the [Surface] composable that surrounds the image should have a border.
 * @param onClick: The function to be executed when the image is clicked.
 */
@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    tag: String = "profile_image",
    photoUrl: String?,
    shape: Shape = RoundedCornerShape(30.dp),
    size: Dp = 120.dp,
    hasBorder: Boolean = false,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .size(size)
            .testTag(tag)
            .clickable { onClick() },
        shape = shape,
        border = if (hasBorder) BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary) else null,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = if (photoUrl != null) {
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUrl)
                        .build()
                )
            } else {
                painterResource(id = R.drawable.profile_placeholder)
            },
            contentDescription = "profile picture",
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
