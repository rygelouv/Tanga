package app.books.tanga.errors

/**
 * Convert an [OperationError] to a [UiError].
 *
 * [OperationError.message] is used as the [UiErrorInfo.title] and a message is provided depending on the type of error.
 */
fun OperationError.toUiError(): UiError {
    return when (this) {
        is OperationError.NoInternetConnectionError -> UiError(
            info = UiErrorInfo(
                title = message,
                message = "Please check your internet connection and try again",
                icon = null
            ),
            throwable = throwable
        )
        is OperationError.UnauthorizedOperationError -> UiError(
            info = UiErrorInfo(
                title = message,
                message = "Please login or create an account to continue",
                icon = null
            ),
            throwable = throwable
        )
        is OperationError.MaintenanceError -> UiError(
            info = UiErrorInfo(
                title = message,
                message = "Sorry, please try again later",
                icon = null
            ),
            throwable = throwable
        )
        is OperationError.ResourceNotFoundError -> UiError(
            info = UiErrorInfo(
                title = message,
                message = "Sorry, the resource you are looking for was not found",
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
                title = message,
                message = "Please login or create an account to continue",
                icon = null
            ),
            throwable = throwable
        )
    }
}