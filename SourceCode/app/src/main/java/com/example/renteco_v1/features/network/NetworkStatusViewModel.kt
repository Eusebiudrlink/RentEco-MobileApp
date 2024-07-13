package com.example.renteco_v1.features.network



import android.app.Application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
class NetworkStatusViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableStateOf(false)
        private set

    init {
        collectNetworkStatus()
    }

    private fun collectNetworkStatus() {
        viewModelScope.launch {
            ConnectivityManagerNetworkMonitor(getApplication()).isOnline.collect {
                uiState = it;
            }
        }
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NetworkStatusViewModel(application)
            }
        }
    }
}