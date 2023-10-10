package app.books.tanga

import android.app.Application
import app.books.tanga.di.TimberTrees
import app.books.tanga.di.plantAll
import app.books.tanga.errors.TangaErrorTracker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TangaApp : Application() {
    @Inject
    lateinit var timberTrees: TimberTrees

    @Inject
    lateinit var errorTracker: TangaErrorTracker

    override fun onCreate() {
        super.onCreate()
        // Plant all the Timber trees added to Timber
        timberTrees.plantAll()

        // Initialize the error tracker
        errorTracker.init()
    }
}
