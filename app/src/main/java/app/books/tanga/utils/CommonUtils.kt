package app.books.tanga.utils

import android.os.Build
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException

/**
 * Like [runCatching], but with proper coroutines cancellation handling. Also only catches [Exception] instead of [Throwable].
 *
 * Cancellation exceptions need to be rethrown. See https://github.com/Kotlin/kotlinx.coroutines/issues/1814.
 */
inline fun <R> resultOf(block: () -> R): Result<R> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

fun randomUid() = UUID.randomUUID().toString()

/**
 * A utility class for checking whether the current Android version on the device
 * or higher.
 *
 * This class is a workaround and is primarily intended for use in unit tests, where `Build.VERSION.SDK_INT`
 * is not available.
 */
object BuildVersionChecker {
    fun isAtLeastOreo() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    fun isAtLeastTiramisu() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
