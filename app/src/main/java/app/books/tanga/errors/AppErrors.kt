package app.books.tanga.errors

import app.books.tanga.R
import app.books.tanga.coreui.resources.TextResource
import app.books.tanga.coreui.resources.asStringOrEmpty

/**
 * Represents all application-level errors in the system.
 *
 * This is a sealed class hierarchy, allowing for a controlled set of subclasses that represent
 * specific types of errors throughout the application.
 *
 * @property appMessage The user-friendly message to be displayed to the user.
 * @property exceptionMessage The detail message, which can later be retrieved by the [Throwable.message] property.
 * @property cause The cause of this error, which can later be retrieved by the [Throwable.cause] property.
 */
sealed class AppError(
    val appMessage: TextResource? = null,
    val exceptionMessage: String? = null,
    cause: Throwable? = null
) : Exception(exceptionMessage, cause)

/**
 * Represents errors that are related to operations, such as network operations, database operations, etc.
 *
 * These errors are typically encountered when performing tasks and need to be conveyed to the user.
 */
sealed class OperationError(
    appMessage: TextResource? = null,
    exceptionMessage: String? = null,
    cause: Throwable? = null
) : AppError(appMessage = appMessage, exceptionMessage = exceptionMessage, cause = cause) {
    /** Represents an error due to no internet connectivity. */
    data class NoInternetConnectionError(
        val throwable: Throwable? = null
    ) : OperationError(
        appMessage = TextResource.fromStringId(R.string.error_no_internet_title),
        exceptionMessage = "No internet connection",
        cause = throwable
    )

    /** Represents an error where an unauthorized operation was attempted. */
    data class UnauthorizedOperationError(
        val throwable: Throwable? = null
    ) : OperationError(
        appMessage = TextResource.fromStringId(R.string.error_unauthorized_operation_title),
        exceptionMessage = "Unauthorized operation",
        cause = throwable
    )

    /** Represents an error indicating that the service is currently on maintenance. */
    data class MaintenanceError(
        val throwable: Throwable? = null
    ) : OperationError(
        appMessage = TextResource.fromStringId(R.string.error_maintenance_title),
        exceptionMessage = "Service on Maintenance",
        cause = throwable
    )

    /** Represents an error indicating that a resource was not found. */
    data class ResourceNotFoundError(
        val throwable: Throwable? = null
    ) : OperationError(
        appMessage = TextResource.fromStringId(R.string.error_resource_not_found_title),
        exceptionMessage = "Resource not found",
        cause = throwable
    )

    /** Represents any error that doesn't fall under other predefined categories. */
    data class UnknownError(
        val throwable: Throwable
    ) : OperationError(
        appMessage = TextResource.fromStringId(R.string.error_unknown_title),
        exceptionMessage = "Unknown error",
        cause = throwable
    )
}

/**
 * Represents domain-specific errors, typically those that relate to business logic.
 *
 * These errors are usually encountered during business rule validation or checks.
 */
sealed class DomainError(
    appMessage: TextResource,
    exceptionMessage: String? = null,
    cause: Throwable? = null
) : AppError(appMessage = appMessage, exceptionMessage = exceptionMessage, cause = cause) {
    /** Represents an error indicating that a user is not authenticated. */
    data class UserNotAuthenticatedError(
        val throwable: Throwable? = null
    ) : DomainError(
        appMessage = TextResource.fromStringId(R.string.error_user_not_authenticated_title),
        exceptionMessage = "User not authenticated",
        cause = throwable
    )

    /** Represents an error indicating that an authentication operation has failed. */
    data class AuthenticationError(
        val throwable: Throwable? = null
    ) : DomainError(
        appMessage = TextResource.fromStringId(R.string.error_authentication_title),
        exceptionMessage = "Authentication operation error",
        cause = throwable
    )

    /** Represents an error indicating that the favorite was not found */
    data class FavoriteNotFoundError(
        val throwable: Throwable? = null
    ) : DomainError(
        appMessage = TextResource.fromStringId(R.string.error_favorite_not_found_title),
        exceptionMessage = "Favorite not found",
        cause = throwable
    )

    /** Represents an error indicating that Google sign-in launch has failed */
    data class UnableToSignInWithGoogleError(
        val throwable: Throwable? = null
    ) : DomainError(
        appMessage = TextResource.fromStringId(R.string.error_unable_to_sign_in_with_google_title),
        exceptionMessage = "User to launch Google sign in",
        cause = throwable
    )
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
) : AppError(appMessage = info.title, exceptionMessage = info.message?.asStringOrEmpty(), cause = throwable)
