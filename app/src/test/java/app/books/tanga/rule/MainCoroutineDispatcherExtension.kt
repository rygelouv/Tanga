package app.books.tanga.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * This is JUnit5 extension class used to set the main dispatcher to a test dispatcher.
 * JUnit5 extensions are the equivalent of JUnit4 rules.
 */
@ExperimentalCoroutinesApi
class MainCoroutineDispatcherExtension(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun close() {
        Dispatchers.resetMain()
    }
}
