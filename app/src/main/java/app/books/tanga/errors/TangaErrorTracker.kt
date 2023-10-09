package app.books.tanga.errors

import android.content.Context
import app.books.tanga.entity.UserId
import java.util.Date
import javax.inject.Inject

/**
 * `TangaErrorTracker` provides an error tracking mechanism by consolidating
 * tracking functionalities of Firebase Crashlytics and Sentry.
 *
 * This class abstracts the tracking details of individual tracking systems
 * (like Firebase Crashlytics and Sentry) and provides a unified interface
 * for error tracking and user-related operations.
 *
 * @param firebaseCrashlyticsUserTracker An instance responsible for tracking
 * errors and user details in Firebase Crashlytics.
 * @param sentryTracker An instance responsible for tracking errors
 * and user details in Sentry.
 * @param context The application's context.
 */
class TangaErrorTracker @Inject constructor(
    private val firebaseCrashlyticsUserTracker: FirebaseCrashlyticsUserTracker,
    private val sentryTracker: SentryTracker,
    private val context: Context
) {

    /**
     * Initializes the error trackers.
     */
    fun init() {
        sentryTracker.init(context = context)
    }

    /**
     * Sets the user details for the error trackers.
     * Both Firebase Crashlytics and Sentry will be updated with the provided user details.
     *
     * @param userId The unique identifier of the user.
     * @param userCreationDate The creation date of the user.
     */
    fun setUserDetails(userId: UserId, userCreationDate: Date) {
        firebaseCrashlyticsUserTracker.setUserDetails(userId, userCreationDate)
        sentryTracker.setUserDetails(userId, userCreationDate)
    }

    /**
     * Clears the user details from the error trackers.
     * This ensures that subsequent errors are not associated with a specific user.
     */
    fun clearUserDetails() {
        firebaseCrashlyticsUserTracker.clearUserDetails()
        sentryTracker.clearUserDetails()
    }
}
