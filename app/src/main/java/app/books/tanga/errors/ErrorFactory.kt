package app.books.tanga.errors

import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Factory to create [AppError] from [FirebaseFirestoreException]
 *
 * Only the most common errors are handled here. We return [OperationError.UnknownError] for all other errors.
 * For more information on the error codes, see: https://firebase.google.com/docs/reference/android/com/google/firebase/firestore/FirebaseFirestoreException.Code
 */
object FirestoreErrorFactory {
    fun createError(exception: FirebaseFirestoreException): AppError {
        return when (exception.code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED -> {
                OperationError.UnauthorizedOperationError(exception)
            }
            FirebaseFirestoreException.Code.UNAVAILABLE -> {
                OperationError.MaintenanceError(exception)
            }
            FirebaseFirestoreException.Code.NOT_FOUND -> {
                OperationError.ResourceNotFoundError(exception)
            }
            else -> {
                OperationError.UnknownError(exception)
            }
        }
    }
}