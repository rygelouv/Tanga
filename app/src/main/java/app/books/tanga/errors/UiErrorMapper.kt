package app.books.tanga.errors

import app.books.tanga.R
import app.books.tanga.core_ui.resources.TextResource

/**
 * Convert an [OperationError] to a [UiError].
 *
 * [OperationError.message] is used as the [UiErrorInfo.title] and a message is provided depending on the type of error.
 */
fun OperationError.toUiError(): UiError {
    return when (this) {
        is OperationError.NoInternetConnectionError -> UiError(
            info = UiErrorInfo(
                title = appMessage,
                message = TextResource.fromStringId(R.string.error_no_internet_message),
                icon = null
            ),
            throwable = throwable
        )
        is OperationError.UnauthorizedOperationError -> UiError(
            info = UiErrorInfo(
                title = appMessage,
                message = TextResource.fromStringId(R.string.error_unauthorized_operation_message),
                icon = null
            ),
            throwable = throwable
        )
        is OperationError.MaintenanceError -> UiError(
            info = UiErrorInfo(
                title = appMessage,
                message = TextResource.fromStringId(R.string.error_maintenance_message),
                icon = null
            ),
            throwable = throwable
        )
        is OperationError.ResourceNotFoundError -> UiError(
            info = UiErrorInfo(
                title = appMessage,
                message = TextResource.fromStringId(R.string.error_resource_not_found_message),
                icon = null
            ),
            throwable = throwable
        )
        is OperationError.UnknownError -> UiError(
            info = UiErrorInfo(),
            throwable = throwable
        )
    }
}

/**
 * Convert a [DomainError] to a [UiError].
 *
 * [DomainError.message] is used as the [UiErrorInfo.title] and a message is provided depending on the type of error.
 */
fun DomainError.toUiError(): UiError {
    return when (this) {
        is DomainError.UserNotAuthenticatedError -> UiError(
            info = UiErrorInfo(
                title = appMessage,
                message = TextResource.fromStringId(R.string.error_user_not_authenticated_message),
                icon = null
            ),
            throwable = throwable
        )
    }
}