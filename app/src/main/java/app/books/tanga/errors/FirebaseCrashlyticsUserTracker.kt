package app.books.tanga.errors

import app.books.tanga.entity.UserId
import app.books.tanga.utils.toFormattedString
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.util.Date
import javax.inject.Inject

private const val USER_CREATED_AT_KEY = "userCreationDate"

/**
 * Manages user-specific tracking in Firebase Crashlytics.
 *
 * This class provides functionality to set and clear user details like ID and creation date
 * in Firebase Crashlytics. By doing so, it aids in filtering and understanding crash reports
 * on a per-user basis.
 */
class FirebaseCrashlyticsUserTracker @Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
) {
    /**
     * Set user-specific details (ID and creation date) to Firebase Crashlytics.
     */
    fun setUserDetails(
        userId: UserId,
        createdAt: Date,
    ) {
        crashlytics.setUserId(userId.value)
        crashlytics.setCustomKey(USER_CREATED_AT_KEY, createdAt.toFormattedString())
    }

    /**
     * Clear user-specific details from Firebase Crashlytics.
     */
    fun clearUserDetails() {
        // FirebaseCrashlytics doesn't provide a direct method to remove the set userId or custom keys.
        // Therefore, we reset by setting them with empty or default values.
        crashlytics.setUserId("")
        crashlytics.setCustomKey(USER_CREATED_AT_KEY, "")
    }
}
