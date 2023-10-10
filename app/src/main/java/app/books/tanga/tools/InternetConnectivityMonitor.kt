package app.books.tanga.tools

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import app.books.tanga.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Interface for monitoring the state of internet connectivity.
 */
interface InternetConnectivityMonitor {
    /**
     * Represents a continuous stream of the internet availability state.
     */
    val isInternetAvailable: StateFlow<Boolean>
}

/**
 * Implementation of [InternetConnectivityMonitor] using Android's [ConnectivityManager].
 *
 * @param connectivityManager Android's connectivity system service to listen to network changes.
 * @param ioDispatcher A dispatcher dedicated for IO operations, ideal for network operations.
 */
class InternetConnectivityMonitorImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : InternetConnectivityMonitor {
    // Dedicated scope for coroutines related to monitoring connectivity.
    private val coroutineScope = CoroutineScope(ioDispatcher)

    /**
     * Lazy initialized [StateFlow] that emits internet availability states.
     */
    override val isInternetAvailable: StateFlow<Boolean> by lazy {
        observeConnectivity().stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue =
            connectivityManager
                .activeNetworkCapabilities()
                ?.hasInternetConnection() ?: false
        )
    }

    /**
     * Observes changes in the connectivity and emits them as a [Flow] of [Boolean].
     *
     * @return [Flow] of Boolean where true indicates the internet is available and false otherwise.
     */
    private fun observeConnectivity(): Flow<Boolean> =
        callbackFlow {
            val callback: NetworkCallback =
                object : NetworkCallback() {
                    override fun onCapabilitiesChanged(
                        network: Network,
                        networkCapabilities: NetworkCapabilities
                    ) {
                        // Emit internet availability state based on network capabilities.
                        trySend(networkCapabilities.hasInternetConnection())
                    }

                    override fun onLost(network: Network) {
                        // Emit false when the network is lost.
                        trySend(false)
                    }
                }

            // Register to receive changes in the default network.
            connectivityManager.registerDefaultNetworkCallback(callback)

            // Ensure to unregister the callback when the flow collection is stopped.
            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }

    /**
     * Fetches the current active network's capabilities.
     */
    private fun ConnectivityManager.activeNetworkCapabilities(): NetworkCapabilities? =
        getNetworkCapabilities(
            activeNetwork
        )

    /**
     * Checks if the [NetworkCapabilities] indicates an active internet connection.
     *
     * Takes into account the [Build.VERSION_CODES.P] behavior change.
     */
    private fun NetworkCapabilities.hasInternetConnection(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            hasInternetAndIsValid() && hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)
        } else {
            hasInternetAndIsValid()
        }

    /**
     * Checks if the [NetworkCapabilities] has both internet and validated capabilities.
     */
    private fun NetworkCapabilities.hasInternetAndIsValid() =
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}
