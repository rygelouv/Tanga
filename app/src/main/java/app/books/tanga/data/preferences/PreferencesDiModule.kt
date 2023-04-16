package app.books.tanga.data.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesDiModule {
    @Provides
    @Singleton
    fun provideDefaultPreDataStoreModule(
        @ApplicationContext context: Context
    ) = DefaultPrefDataStoreRepository(context = context)
}