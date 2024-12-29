package app.books.tanga.notifications.di

import android.app.NotificationManager
import android.content.Context
import app.books.tanga.di.IoDispatcher
import app.books.tanga.notifications.NotificationHandler
import app.books.tanga.notifications.NotificationHandlerImpl
import app.books.tanga.notifications.NotificationPermissionHandler
import app.books.tanga.notifications.NotificationPermissionHandlerImpl
import app.books.tanga.notifications.TangaNotificationBuilder
import app.books.tanga.notifications.TangaNotificationBuilderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
interface NotificationsModule {

    @Binds
    fun bindNotificationHandler(notificationHandler: NotificationHandlerImpl): NotificationHandler

    @Binds
    fun bindNotificationBuilder(notificationBuilder: TangaNotificationBuilderImpl): TangaNotificationBuilder

    @Binds
    fun bindNotificationPermissionHandler(
        notificationPermissionHandler: NotificationPermissionHandlerImpl
    ): NotificationPermissionHandler

    companion object {
        @Provides
        @Singleton
        fun providePushNotificationManager(@ApplicationContext context: Context): NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        @NotificationsCoroutineScope
        @Provides
        fun provideNotificationsCoroutineScope(
            @IoDispatcher ioDispatcher: CoroutineDispatcher
        ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NotificationsCoroutineScope
