package app.books.tanga.feature.audioplayer

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AudioPlayerModule {
    @Provides
    fun provideExoPlayer(
        @ApplicationContext context: Context
    ): ExoPlayer = ExoPlayer.Builder(context).build()

    @Provides
    fun providePlayerController(exoPlayerController: ExoPlayerController): PlayerController = exoPlayerController
}
