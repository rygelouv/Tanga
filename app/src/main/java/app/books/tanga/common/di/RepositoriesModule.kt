package app.books.tanga.common.di

import app.books.tanga.common.domain.UserRepository
import app.books.tanga.common.domain.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun UserRepositoryImpl.provideUserRepository(): UserRepository
}