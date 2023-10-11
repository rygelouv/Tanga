package app.books.tanga.errors

import app.books.tanga.R
import app.books.tanga.coreui.resources.TextResource

/**
 * Converts a [Throwable] into a [UiError].
 *
 * This function translates known application-specific error types into their corresponding UI errors.
 * If the error type isn't recognized as one of the domain-specific errors, it defaults to an unknown operation error.
 *
 */
fun Throwable.toUiError(): UiError =
    when (this) {
        is OperationError -> this.toUiError()
        is DomainError -> this.toUiError()
        else -> OperationError.UnknownError(this).toUiError()
    }

/**
 * Convert an [OperationError] to a [UiError].
 *
 * [OperationError.message] is used as the [UiErrorInfo.title] and a message is provided depending on the type of error.
 */
fun OperationError.toUiError(): UiError =
    when (this) {
        is OperationError.NoInternetConnectionError ->
            UiError(
                info = UiErrorInfo(
                    title = appMessage,
                    message = TextResource.fromStringId(R.string.error_no_internet_message),
                    icon = null
                ),
                throwable = throwable
            )

        is OperationError.UnauthorizedOperationError ->
            UiError(
                info = UiErrorInfo(
                    title = appMessage,
                    message = TextResource.fromStringId(R.string.error_unauthorized_operation_message),
                    icon = null
                ),
                throwable = throwable
            )

        is OperationError.MaintenanceError ->
            UiError(
                info = UiErrorInfo(
                    title = appMessage,
                    message = TextResource.fromStringId(R.string.error_maintenance_message),
                    icon = null
                ),
                throwable = throwable
            )

        is OperationError.ResourceNotFoundError ->
            UiError(
                info = UiErrorInfo(
                    title = appMessage,
                    message = TextResource.fromStringId(R.string.error_resource_not_found_message),
                    icon = null
                ),
                throwable = throwable
            )

        is OperationError.UnknownError ->
            UiError(
                info = UiErrorInfo(),
                throwable = throwable
            )
    }

/**
 * Convert a [DomainError] to a [UiError].
 *
 * [DomainError.message] is used as the [UiErrorInfo.title] and a message is provided depending on the type of error.
 */
fun DomainError.toUiError(): UiError =
    when (this) {
        is DomainError.UserNotAuthenticatedError ->
            UiError(
                info = UiErrorInfo(
                    title = appMessage,
                    message = TextResource.fromStringId(R.string.error_user_not_authenticated_message),
                    icon = null
                ),
                throwable = throwable
            )

        is DomainError.FavoriteNotFoundError ->
            UiError(
                info = UiErrorInfo(
                    title = appMessage,
                    message = TextResource.fromStringId(R.string.error_favorite_not_found_message),
                    icon = null
                ),
                throwable = throwable
            )

        is DomainError.UnableToSignInWithGoogleError ->
            UiError(
                info = UiErrorInfo(
                    title = appMessage,
                    message = TextResource.fromStringId(R.string.error_unable_to_sign_in_with_google_message),
                    icon = null
                ),
                throwable = throwable
            )
    }
