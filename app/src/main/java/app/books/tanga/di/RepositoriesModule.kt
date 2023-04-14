package app.books.tanga.di

import app.books.tanga.data.UserRepository
import app.books.tanga.data.UserRepositoryImpl
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