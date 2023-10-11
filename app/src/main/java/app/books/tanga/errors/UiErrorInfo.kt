package app.books.tanga.errors

import androidx.annotation.DrawableRes
import app.books.tanga.coreui.resources.TextResource

/**
 * Represents user-friendly error information to be displayed on the UI.
 *
 * This class encapsulates the necessary elements to provide a comprehensive and user-friendly
 * error message to end-users. It can include a title, a message, and an associated icon.
 * This makes it convenient to pass around error details throughout the UI components that need
 * to display error feedback to users.
 *
 * @property title An optional title for the error, providing a brief headline or category of the error.
 * @property message An optional detailed message describing the error. This is typically presented to the user.
 * @property icon An optional drawable resource identifier pointing to an icon representing the error visually.
 */
data class UiErrorInfo(
    val title: TextResource? = null,
    val message: TextResource? = null,
    @DrawableRes val icon: Int? = null
)
