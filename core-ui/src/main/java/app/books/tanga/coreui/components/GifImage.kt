package app.books.tanga.coreui.components

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@Composable
fun DisplayGifAsset(
    assetName: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val context = LocalContext.current
    val asset = context.assets.open(assetName).readBytes()
    Log.e("DisplayGifAsset", "assetName: $assetName, Size =====> ${asset.size}")

    val imageLoader = ImageLoader.Builder(context).components {
        if (SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()

    AsyncImage(
        modifier = modifier.size(300.dp),
        imageLoader = imageLoader,
        model = ImageRequest.Builder(context)
            .data(asset)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription
    )
}
