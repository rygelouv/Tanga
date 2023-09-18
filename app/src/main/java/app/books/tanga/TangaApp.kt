package app.books.tanga

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TangaApp: Application(), ImageLoaderFactory {

        override fun newImageLoader() = ImageLoader.Builder(this)
            .logger(DebugLogger())
            .crossfade(true)
            .build()
}