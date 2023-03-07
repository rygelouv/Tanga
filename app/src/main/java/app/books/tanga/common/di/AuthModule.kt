package app.books.tanga.common.di

import app.books.tanga.common.domain.AuthenticationInteractor
import app.books.tanga.common.domain.AuthenticationInteractorImpl
import dagger.Binds

interface AuthModule {

    @Binds
    fun AuthenticationInteractorImpl.provideAuthenticationInteractor(): AuthenticationInteractor
}