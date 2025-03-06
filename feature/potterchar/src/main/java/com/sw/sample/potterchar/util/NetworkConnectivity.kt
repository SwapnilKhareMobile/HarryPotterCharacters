package com.sw.sample.potterchar.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed interface NetworkConnectionState {
    data object Available : NetworkConnectionState
    data object Unavailable : NetworkConnectionState
}

class NetworkConnectivity {
    private fun networkCallback(callback: (NetworkConnectionState) -> Unit): ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                callback(NetworkConnectionState.Available)
            }

            override fun onUnavailable() {
                callback(NetworkConnectionState.Unavailable)
            }
        }

    private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): NetworkConnectionState {
        val network = connectivityManager.activeNetwork
        val isConnected = connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

        return if (isConnected) NetworkConnectionState.Available else NetworkConnectionState.Unavailable
    }

    private fun Context.observeConnectivityAsFlow(): Flow<NetworkConnectionState> = callbackFlow {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val callback = networkCallback { connectionState ->
            trySend(connectionState)
        }
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, callback)

        val currentState = getCurrentConnectivityState(connectivityManager)
        trySend(currentState)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    private val Context.currentConnectivityState: NetworkConnectionState
        get() {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return getCurrentConnectivityState(connectivityManager)
        }

    @ExperimentalCoroutinesApi
    @Composable
    fun rememberConnectivityState(): State<NetworkConnectionState> {
        val context = LocalContext.current

        return produceState(initialValue = context.currentConnectivityState) {
            context.observeConnectivityAsFlow().collect {
                value = it
            }
        }
    }
}