package app.books.tanga.feature.audioplayer

import android.content.ComponentName
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
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
    fun providePlayerController(playerControllerImpl: PlayerControllerImpl): PlayerController = playerControllerImpl

    @Provides
    fun provideMediaControllerBuilder(@ApplicationContext context: Context): MediaController.Builder {
        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        return MediaController.Builder(context, sessionToken)
    }
}
