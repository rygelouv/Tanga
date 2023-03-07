package app.books.tanga.common.di

import app.books.tanga.common.domain.UserRepository
import app.books.tanga.common.domain.UserRepositoryImpl
import dagger.Binds

interface RepositoriesModule {

    @Binds
    fun UserRepositoryImpl.provideUserRepository(): UserRepository
}