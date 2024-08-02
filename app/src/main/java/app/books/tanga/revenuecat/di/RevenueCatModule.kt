package app.books.tanga.revenuecat.di

import app.books.tanga.revenuecat.RevenueCatAuthenticator
import app.books.tanga.revenuecat.RevenueCatController
import app.books.tanga.revenuecat.RevenueCatInitializer
import app.books.tanga.revenuecat.RevenueCatPurchases
import com.revenuecat.purchases.Purchases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RevenueCatModule {

    @Binds
    fun provideRevenueCatInitializer(impl: RevenueCatController): RevenueCatInitializer

    @Binds
    fun provideRevenueCatAuthenticator(impl: RevenueCatController): RevenueCatAuthenticator

    @Binds
    fun provideRevenueCatPurchase(impl: RevenueCatController): RevenueCatPurchases

    companion object {
        @Provides
        fun providePurchases(): Purchases = Purchases.sharedInstance
    }
}
