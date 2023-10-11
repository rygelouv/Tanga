package app.books.tanga.coreui.resources

import android.content.res.Resources
import androidx.annotation.StringRes

/**
 * A sealed class representing a text resource which can either be a direct string
 * or a reference to a string resource ID.
 *
 * See: https://hannesdorfmann.com/abstraction-text-resource/
 */
sealed class TextResource {
    companion object {
        /**
         * Create a [TextResource] instance from a given text.
         */
        fun fromText(text: String): TextResource = SimpleTextResource(text)

        /**
         * Create a [TextResource] instance from a string resource ID.
         */
        fun fromStringId(
            @StringRes id: Int,
        ): TextResource = IdTextResource(id)
    }
}

/**
 * Represents a [TextResource] holding a string resource ID.
 */
class IdTextResource(
    @StringRes val id: Int
) : TextResource()

/**
 * Represents a [TextResource] holding a direct text string.
 */
private data class SimpleTextResource(
    val text: String,
) : TextResource()

/**
 * Extension function to get the string representation of [TextResource].
 *
 * @param resources The resources to fetch string if the resource is an [IdTextResource].
 */
fun TextResource.asString(resources: Resources): String = when (this) {
    is SimpleTextResource -> this.text
    is IdTextResource -> resources.getString(this.id)
}

/**
 * Extension function to get the string representation of [TextResource] or an empty string.
 *
 * @return The actual string text if [TextResource] is a [SimpleTextResource] or
 * an empty string if it's an [IdTextResource].
 */
fun TextResource.asStringOrEmpty(): String = when (this) {
    is SimpleTextResource -> this.text
    is IdTextResource -> ""
}
