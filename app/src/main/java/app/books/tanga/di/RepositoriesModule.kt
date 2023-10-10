package app.books.tanga.di

import app.books.tanga.data.category.CategoryRepository
import app.books.tanga.data.category.CategoryRepositoryImpl
import app.books.tanga.data.favorite.FavoriteRepository
import app.books.tanga.data.favorite.FavoriteRepositoryImpl
import app.books.tanga.data.summary.SummaryRepository
import app.books.tanga.data.summary.SummaryRepositoryImpl
import app.books.tanga.data.user.UserRepository
import app.books.tanga.data.user.UserRepositoryImpl
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

    @Binds
    fun FavoriteRepositoryImpl.provideFavoriteRepository(): FavoriteRepository
}
