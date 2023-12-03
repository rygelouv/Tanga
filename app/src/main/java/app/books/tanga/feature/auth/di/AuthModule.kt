package app.books.tanga.feature.auth.di

import app.books.tanga.feature.auth.AnonymousAuthService
import app.books.tanga.feature.auth.AnonymousAuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun AnonymousAuthServiceImpl.provideAnonymousAuthService(): AnonymousAuthService
}
