package com.example.renteco_v1.rental_items.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import com.example.renteco_v1.rental_items.data.repo.VehicleRepository
import com.example.renteco_v1.user_access.login.data.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class VehicleListViewModel(
    private val vehicleRepository: VehicleRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val rentingState= mutableStateOf<Boolean>(false)
    val uiState: Flow<List<AutoVehicle>> = vehicleRepository.itemStream
    val expiredJWTState = mutableStateOf<Boolean>(false)

    init {
        Log.d("VehiclesViewModel", "init")
            viewModelScope.launch {
                Api.currentUser = authRepository.getUser(Api.currentEmail)
                if(Api.currentUser.id==0) {
                    Log.e("ViewModel", "Error loading User. Maybe JWT is expired or server is down.")
                    expiredJWTState.value = true
                }
                if(Api.currentUser.inrent!=0 && Api.alreadyRunning==true) {
                    Log.e("ViewModel", "User is already renting a vehicle")
                    rentingState.value = true
                    Api.alreadyRunning=false
                }
                else{
                    Api.alreadyRunning=false
                    rentingState.value=false
                }
            }
        loadItems()
    }
    fun setRentingState(value:Boolean) {
        rentingState.value = value
    }

     fun loadItems() {
        Log.d(TAG, "load items")

        viewModelScope.launch {
            vehicleRepository.refresh()
        }

    }


    companion object { //daca are companion si object inseamna ca e singleton
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                VehicleListViewModel(app.container.vehicleRepository, app.container.authRepository)
            }
        }
    }

}