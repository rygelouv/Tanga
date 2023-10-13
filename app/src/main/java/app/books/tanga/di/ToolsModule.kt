package app.books.tanga.di

import app.books.tanga.tools.InternetConnectivityMonitor
import app.books.tanga.tools.InternetConnectivityMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InternetConnectivityMonitorModule {
    @Binds
    abstract fun InternetConnectivityMonitorImpl.provideInternetConnectivityMonitor(): InternetConnectivityMonitor
}
