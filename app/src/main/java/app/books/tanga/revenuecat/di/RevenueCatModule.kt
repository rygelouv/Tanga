package app.books.tanga.revenuecat.di

import app.books.tanga.revenuecat.RevenueCatAuthenticator
import app.books.tanga.revenuecat.RevenueCatController
import app.books.tanga.revenuecat.RevenueCatInitializer
import app.books.tanga.revenuecat.RevenueCatPurchases
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RevenueCatModule {

    @Binds
    fun RevenueCatController.provideRevenueCatInitializer(): RevenueCatInitializer

    @Binds
    fun RevenueCatController.provideRevenueCatAuthenticator(): RevenueCatAuthenticator

    @Binds
    fun RevenueCatController.provideRevenueCatPurchase(): RevenueCatPurchases
}
