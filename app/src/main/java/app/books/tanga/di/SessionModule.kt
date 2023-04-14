package app.books.tanga.di

import app.books.tanga.data.PrefSessionManager
import app.books.tanga.domain.session.SessionManager
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