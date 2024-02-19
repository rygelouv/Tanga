package app.books.tanga.feature.summary.di

import app.books.tanga.feature.summary.SummaryBehaviorDelegate
import app.books.tanga.feature.summary.SummaryBehaviorDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SummaryModule {

    @Binds
    abstract fun SummaryBehaviorDelegateImpl.provideSummaryBehaviorEngine(): SummaryBehaviorDelegate
}
