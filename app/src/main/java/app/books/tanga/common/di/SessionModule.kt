package app.books.tanga.common.di

import app.books.tanga.common.data.PrefSessionManager
import app.books.tanga.common.domain.session.SessionManager
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