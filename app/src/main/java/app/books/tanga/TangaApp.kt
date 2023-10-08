package app.books.tanga

import android.app.Application
import app.books.tanga.di.TimberTrees
import app.books.tanga.di.plantAll
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TangaApp : Application() {

    @Inject
    lateinit var timberTrees: TimberTrees

    override fun onCreate() {
        super.onCreate()
        // Plant all the Timber trees add to Timber
        timberTrees.plantAll()
    }
}