package app.books.tanga.di

import app.books.tanga.data.CategoryRepository
import app.books.tanga.data.CategoryRepositoryImpl
import app.books.tanga.data.SummaryRepository
import app.books.tanga.data.SummaryRepositoryImpl
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

    @Binds
    fun SummaryRepositoryImpl.provideSummaryRepository(): SummaryRepository

    @Binds
    fun CategoryRepositoryImpl.provideCategoryRepository(): CategoryRepository
}