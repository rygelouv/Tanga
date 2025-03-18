package app.books.tanga

import android.app.Application
import app.books.tanga.di.TimberTrees
import app.books.tanga.di.plantAll
import app.books.tanga.errors.TangaErrorTracker
import app.books.tanga.revenuecat.RevenueCatInitializer
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TangaApp : Application() {
    @Inject
    lateinit var timberTrees: TimberTrees

    @Inject
    lateinit var errorTracker: TangaErrorTracker

    @Inject
    lateinit var revenueCatInitializer: RevenueCatInitializer

    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase App Check
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )

        // Plant all the Timber trees added to Timber
        timberTrees.plantAll()

        // Initialize the error tracker
        errorTracker.init()

        revenueCatInitializer.initialize(this)
    }
}
