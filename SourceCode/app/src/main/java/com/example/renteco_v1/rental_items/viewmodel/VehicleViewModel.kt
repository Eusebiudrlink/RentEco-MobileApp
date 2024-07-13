package com.example.renteco_v1.rental_items.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapp.core.ui.UserPreferencesViewModel
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import com.example.renteco_v1.rental_items.data.repo.VehicleRepository
import com.example.renteco_v1.features.Result
import com.example.renteco_v1.features.localPreferences.UserPreferences
import com.example.renteco_v1.features.localPreferences.UserPreferencesRepository
import com.example.renteco_v1.user_access.login.data.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

data class VehicleUiState(
    val vehicleId: Int? = 0,
    val vehicle: AutoVehicle = AutoVehicle(),
    val loadResult: Result<AutoVehicle>? = null,
    )

class VehicleViewModel(
    private val vehicleId: Int?,
    private val vehicleRepository: VehicleRepository,
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferencesRepository
) : ViewModel() {

    var uiState: VehicleUiState by mutableStateOf(VehicleUiState(loadResult = Result.Loading))
    var uiRentStatus:Int by mutableStateOf<Int>(0)


    init {
        loadVehicle()
    }

    private fun loadVehicle() {
        viewModelScope.launch {
            vehicleRepository.itemStream.collect { vehicles ->
                if (!(uiState.loadResult is Result.Loading)) {
                    return@collect
                }
                val vehicle = vehicles.find({ it.id == vehicleId }) ?: AutoVehicle()
                uiState = uiState.copy(vehicle = vehicle, loadResult = Result.Success(vehicle))
            }
        }
    }

    public fun updateVehicleStatus(vehicle: AutoVehicle, boolean: Boolean):Boolean {
        Log.d("tag","Test ${uiRentStatus} update")

        viewModelScope.launch {
                Log.d("Tag", "Brand vehicle:${vehicle.brand}")
                vehicle.rented=boolean
                val autovehicle = vehicleRepository.updateVehicle(vehicle.id, vehicle)
            Api.startTime= Clock.System.now().toString()
            Log.d("tag","UserPreferences saved with start time ${Api.startTime}")
                if( autovehicle != null){
                    uiRentStatus=1//poate fi inchiriat
                    authRepository.updateUser(Api.currentUser.copy(inrent = vehicle.id))//update user renting status

                    userPreferences.savePreferences(
                        UserPreferences(Api.currentEmail,Api.tokenInterceptor.token?:"", Api.startTime)
                    )
                }
                else{
                    uiRentStatus=2//eroare
                }
               // return@launch able//daca a fost modificat este true

        }
        return false

    }

    companion object { //daca are companion si object inseamna ca e singleton
        fun Factory(vehicleId: Int?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                VehicleViewModel(vehicleId, app.container.vehicleRepository,app.container.authRepository,app.container.userPreferencesRepository)
            }
        }
    }
}