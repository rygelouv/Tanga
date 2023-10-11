package app.books.tanga.errors

import android.content.Context
import app.books.tanga.BuildConfig
import app.books.tanga.di.IoDispatcher
import app.books.tanga.entity.UserId
import app.books.tanga.utils.toFormattedString
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.core.SentryAndroid
import io.sentry.android.timber.SentryTimberIntegration
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val USER_CREATED_AT_KEY = "user_creation_date"

/**
 * `SentryTracker` provides a centralized mechanism for interacting with Sentry,
 * an error and event logging tool.
 *
 * This class encompasses initialization, user details setting, and
 * other functionalities specifically tailored for Sentry.
 */
class SentryTracker @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    /**
     * Initializes the Sentry SDK with specified configurations.
     *
     * The SDK filters out DEBUG level events and adds Timber integration
     * for non-debug builds with a minimum breadcrumb level set to WARNING.
     */
    fun init(context: Context) {
        SentryAndroid.init(
            context
        ) { options ->
            // Filtering out DEBUG level events
            options.beforeSend =
                SentryOptions.BeforeSendCallback { event, _ ->
                    if (event.level == SentryLevel.DEBUG) {
                        null
                    } else {
                        event
                    }
                }

            // Adding Timber integration for non-debug builds
            if (BuildConfig.DEBUG.not()) {
                options.addIntegration(
                    SentryTimberIntegration(
                        minBreadcrumbLevel = SentryLevel.WARNING
                    )
                )
            }
        }
    }

    /**
     * Sets the user details for Sentry.
     *
     * This will allow Sentry to associate subsequent events and errors
     * with the specified user.
     */
    fun setUserDetails(
        userId: UserId,
        userCreationDate: Date
    ) {
        CoroutineScope(ioDispatcher).launch {
            val user = io.sentry.protocol.User().apply { id = userId.value }
            Sentry.setUser(user)
            Sentry.configureScope { scope ->
                scope.setContexts(USER_CREATED_AT_KEY, userCreationDate.toFormattedString())
            }
        }
    }

    /**
     * Clears the user details from Sentry.
     */
    fun clearUserDetails() {
        CoroutineScope(ioDispatcher).launch {
            Sentry.configureScope {
                it.removeContexts(USER_CREATED_AT_KEY)
                it.user = null
            }
        }
    }
}
