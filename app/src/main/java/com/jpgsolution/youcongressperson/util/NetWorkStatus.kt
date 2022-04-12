package com.jpgsolution.youcongressperson.util

import android.content.Context
import android.net.*
import androidx.lifecycle.MutableLiveData


object CheckNetWorkStatus{

    val netWorkStatus: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var connectManager: ConnectivityManager

    fun getNetwork(context: Context) {
        connectManager =
            context.getSystemService(ConnectivityManager::class.java)
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        netWorkStatus.postValue( connectManager.activeNetwork != null)
        connectManager.registerNetworkCallback(request, connectCallback)
    }

    fun unRegisterCallBack(){
        connectManager.unregisterNetworkCallback(connectCallback)
    }

    private val connectCallback = object: ConnectivityManager.NetworkCallback(){

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            netWorkStatus.postValue(false)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            netWorkStatus.postValue(false)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            netWorkStatus.postValue(false)
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties)
            netWorkStatus.postValue(true)
        }

    }
}