package app.books.tanga.session

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import app.books.tanga.tools.InternetConnectivityMonitorImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InternetConnectivityMonitorTest {

    private val connectivityManager: ConnectivityManager = mockk(relaxed = true)

    private val ioDispatcher = UnconfinedTestDispatcher()

    private lateinit var monitor: InternetConnectivityMonitorImpl

    @BeforeEach
    fun setup() {
        monitor = InternetConnectivityMonitorImpl(connectivityManager, ioDispatcher)
    }

    @Test
    fun `monitor returns available internet when network capabilities for internet are present`() = runTest {
        val capabilities: NetworkCapabilities = mockk(relaxed = true)
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED) } returns true
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) } returns true
        every { connectivityManager.getNetworkCapabilities(any()) } returns capabilities

        val isInternetAvailable = monitor.isInternetAvailable.value

        assert(isInternetAvailable)
    }

    @Test
    fun `monitor returns internet NOT available when network capabilities for internet are not present`() = runTest {
        val capabilities: NetworkCapabilities = mockk(relaxed = true)
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns false
        every { connectivityManager.getNetworkCapabilities(any()) } returns capabilities

        val isInternetAvailable = monitor.isInternetAvailable.value

        assert(isInternetAvailable.not())
    }

    @Test
    fun `connectivity callback emits correct values on capabilities change`() = runTest {
        val callbackSlot = slot<ConnectivityManager.NetworkCallback>()
        every { connectivityManager.registerDefaultNetworkCallback(capture(callbackSlot)) } answers {}

        val capabilities: NetworkCapabilities = mockk(relaxed = true)
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED) } returns true
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) } returns true
        every { connectivityManager.getNetworkCapabilities(any()) } returns capabilities

        val isInternetAvailable = monitor.isInternetAvailable.value

        assert(isInternetAvailable)

        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns false

        callbackSlot.captured.onCapabilitiesChanged(mockk(), capabilities)

        val isInternetAvailable2 = monitor.isInternetAvailable.value

        assert(isInternetAvailable2.not())
    }

    @Test
    fun `connectivity callback emits false on network lost`() = runTest {
        val callbackSlot = slot<ConnectivityManager.NetworkCallback>()
        every { connectivityManager.registerDefaultNetworkCallback(capture(callbackSlot)) } answers {}

        val capabilities: NetworkCapabilities = mockk(relaxed = true)
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED) } returns true
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true
        every { capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) } returns true
        every { connectivityManager.getNetworkCapabilities(any()) } returns capabilities

        val isInternetAvailable = monitor.isInternetAvailable.value

        assert(isInternetAvailable)

        callbackSlot.captured.onLost(mockk())

        val isInternetAvailable2 = monitor.isInternetAvailable.value

        assert(isInternetAvailable2.not())
    }
}
