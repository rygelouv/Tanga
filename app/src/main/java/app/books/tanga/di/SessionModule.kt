package app.books.tanga.di

import app.books.tanga.session.PrefSessionManager
import app.books.tanga.session.SessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SessionModule {
    @Binds
    fun PrefSessionManager.provideSessionManager(): SessionManager
}
