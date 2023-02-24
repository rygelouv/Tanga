package app.books.tanga.preferences

import android.content.Context
import app.books.tanga.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesDiModule {
    @Provides
    @Singleton
    fun provideDefaultPreDataStoreModule(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) = DefaultPrefDataStoreRepository(context = context, dispatcher)
}