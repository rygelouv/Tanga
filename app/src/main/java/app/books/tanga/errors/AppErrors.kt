package app.books.tanga.errors

import kotlin.Exception

/**
 * Represents all application-level errors in the system.
 *
 * This is a sealed class hierarchy, allowing for a controlled set of subclasses that represent
 * specific types of errors throughout the application.
 *
 * @property message The detail message, which can later be retrieved by the [Throwable.message] property.
 * @property cause The cause of this error, which can later be retrieved by the [Throwable.cause] property.
 */
sealed class AppError(
    message: String? = null, cause: Throwable? = null
) : Exception(message, cause)

/**
 * Represents errors that are related to operations, such as network operations, database operations, etc.
 *
 * These errors are typically encountered when performing tasks and need to be conveyed to the user.
 */
sealed class OperationError(
    message: String? = null, cause: Throwable? = null
) : AppError(message = message, cause = cause) {

    /** Represents an error due to no internet connectivity. */
    data class NoInternetConnectionError(val throwable: Throwable? = null) :
        OperationError(message = "No internet connection", cause = throwable)

    /** Represents an error where an unauthorized operation was attempted. */
    data class UnauthorizedOperationError(val throwable: Throwable? = null) :
        OperationError(message = "Unauthorized operation", cause = throwable)

    /** Represents an error indicating that the service is currently on maintenance. */
    data class MaintenanceError(val throwable: Throwable? = null) :
        OperationError(message = "Service on Maintenance", cause = throwable)

    /** Represents an error indicating that a resource was not found. */
    data class ResourceNotFoundError(val throwable: Throwable? = null) :
        OperationError(message = "Resource not found", cause = throwable)

    /** Represents any error that doesn't fall under other predefined categories. */
    data class UnknownError(val throwable: Throwable) :
        OperationError(message = "Unknown error", cause = throwable)
}

/**
 * Represents domain-specific errors, typically those that relate to business logic.
 *
 * These errors are usually encountered during business rule validation or checks.
 */
sealed class DomainError(
    message: String? = null, cause: Throwable? = null
) : AppError(message = message, cause = cause) {

    /** Represents an error indicating that a user is not authenticated. */
    data class UserNotAuthenticatedError(val throwable: Throwable? = null) :
        DomainError(message = "User not authenticated", cause = throwable)
}

/**
 * Represents UI-level errors.
 *
 * These errors are usually shown directly to the user, often encapsulating underlying errors for user-friendly presentation.
 *
 * @property info Contains the UI-related error information like title, message, and icon.
 * @property throwable The underlying cause of this error, if available.
 */
data class UiError(
    val info: UiErrorInfo,
    val throwable: Throwable? = null
) : AppError(message = info.message, cause = throwable)
