package app.books.tanga.tracking.di

import app.books.tanga.tracking.AnalyticsProvider
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.AnalyticsTrackerImpl
import app.books.tanga.tracking.FirebaseAnalyticsProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ParametersBuilder
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

typealias AnalyticsProviders = List<@JvmSuppressWildcards AnalyticsProvider>

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Provides
    fun provideFirebaseAnalyticsProvider(
        firebaseAnalytics: FirebaseAnalytics
    ): FirebaseAnalyticsProvider = FirebaseAnalyticsProvider(firebaseAnalytics, ParametersBuilder())

    @Provides
    fun provideAnalyticsProviders(
        firebaseAnalyticsProvider: FirebaseAnalyticsProvider
    ): AnalyticsProviders = listOf(firebaseAnalyticsProvider)

    @Provides
    fun provideAnalyticsTracker(
        analyticsProviders: AnalyticsProviders
    ): AnalyticsTracker = AnalyticsTrackerImpl(analyticsProviders)
}
