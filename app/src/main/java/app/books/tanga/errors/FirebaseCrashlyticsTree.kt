package app.books.tanga.errors

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import timber.log.Timber

/**
 * A [Timber.Tree] that logs to Crashlytics.
 */
class FirebaseCrashlyticsTree @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) : Timber.Tree() {
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        // Ignore VERBOSE and DEBUG logs to prevent noise in Crashlytics
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        // Combine the tag and message, if a tag is present
        val description = tag?.let { "$it: $message" } ?: message

        // Log the description in Crashlytics based on the log priority
        when (priority) {
            Log.INFO -> crashlytics.log(description)
            Log.WARN -> crashlytics.log("w: $description")
            Log.ERROR -> crashlytics.log("e: $description")
        }

        // If there's no throwable and the log priority isn't ERROR, then we don't need to record an exception
        if (t == null && priority != Log.ERROR) return

        // Record the exception in Crashlytics. If no throwable is provided, create a custom non-fatal exception.
        crashlytics.recordException(t ?: CrashlyticsNonFatalException(description))
    }
}

/** Helps tracking non-fatal exceptions in Crashlytics */
private class CrashlyticsNonFatalException(
    override val message: String?
) : Exception(message)
