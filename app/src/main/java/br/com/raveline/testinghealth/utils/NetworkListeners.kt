package br.com.raveline.testinghealth.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListeners : ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableLiveData(false)

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkNetworkAvailability(context: Context): MutableLiveData<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false
        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                    return@forEach
                }
            }
        }

        isNetworkAvailable.value = isConnected
        return isNetworkAvailable

    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.postValue(true)
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.postValue(false)
    }
}