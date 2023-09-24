package app.books.tanga.di

import app.books.tanga.feature.auth.AuthenticationInteractor
import app.books.tanga.feature.auth.AuthenticationInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun AuthenticationInteractorImpl.provideAuthenticationInteractor(): AuthenticationInteractor
}