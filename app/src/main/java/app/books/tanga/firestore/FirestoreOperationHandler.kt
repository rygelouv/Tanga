package app.books.tanga.firestore

import app.books.tanga.errors.FirestoreErrorFactory
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Executes the given Firestore operation and wraps its result or error into a [Result].
 *
 * This function is designed to encapsulate the error handling logic for Firestore operations, ensuring that
 * Firestore-specific exceptions are translated into domain-specific error types.
 *
 * Usage:
 * ```kotlin
 * val result = executeFirestoreOperation { firestore.collection("users").document(userId).get() }
 * when (result) {
 *     is Result.Success -> handleSuccess(result.data)
 *     is Result.Failure -> handleError(result.exception)
 * }
 * ```
 *
 * @param T The type of data returned by the Firestore operation.
 * @param operation The Firestore operation to execute, represented as a suspending lambda.
 *
 * @return [Result] containing either the successfully retrieved data or an [app.books.tanga.errors.AppError].
 */
suspend fun <T> executeFirestoreOperation(
    operation: suspend () -> T
): Result<T> {
    return try {
        Result.success(operation())
    } catch (exception: FirebaseFirestoreException) {
        val error = FirestoreErrorFactory.createError(exception)
        return Result.failure(error)
    }
    /* catch (exception: Exception) {
        // Check if there is internet connection
    }*/
}

