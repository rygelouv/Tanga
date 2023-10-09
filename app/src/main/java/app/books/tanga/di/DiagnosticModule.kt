package app.books.tanga.di

import android.content.Context
import app.books.tanga.BuildConfig
import app.books.tanga.errors.FirebaseCrashlyticsUserTracker
import app.books.tanga.errors.FirebaseCrashlyticsTree
import app.books.tanga.errors.SentryTracker
import app.books.tanga.errors.TangaErrorTracker
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

typealias TimberTrees = ArrayList<Timber.Tree>

fun TimberTrees.plantAll() = forEach { Timber.plant(it) }

@Module
@InstallIn(SingletonComponent::class)
class DiagnosticModule {

    @Singleton
    @Provides
    fun provideFirebaseCrashlytics() = FirebaseCrashlytics.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseCrashlyticsTree(firebaseCrashlytics: FirebaseCrashlytics) =
        FirebaseCrashlyticsTree(firebaseCrashlytics)

    @Singleton
    @Provides
    fun provideCrashlyticsUserTracker(firebaseCrashlytics: FirebaseCrashlytics) =
        FirebaseCrashlyticsUserTracker(firebaseCrashlytics)

    @Singleton
    @Provides
    fun provideTangaErrorTracker(
        firebaseCrashlyticsUserTracker: FirebaseCrashlyticsUserTracker,
        sentryTracker: SentryTracker,
        @ApplicationContext context: Context
    ) = TangaErrorTracker(firebaseCrashlyticsUserTracker, sentryTracker, context)

    @Singleton
    @Provides
    fun provideTimberTrees(firebaseCrashlyticsTree: FirebaseCrashlyticsTree) : TimberTrees {
        val trees = TimberTrees()
        when {
            BuildConfig.DEBUG -> trees.add(Timber.DebugTree())
            else -> trees.add(firebaseCrashlyticsTree)
        }
        return trees
    }
}