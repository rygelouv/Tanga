package app.books.tanga.common.di

import app.books.tanga.common.domain.auth.AuthenticationInteractor
import app.books.tanga.common.domain.auth.AuthenticationInteractorImpl
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