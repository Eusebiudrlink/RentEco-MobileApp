package com.example.renteco_v1.rental_items.viewmodel

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.features.Result
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.features.arduino.generateMessage
import com.example.renteco_v1.features.location.LocationData
import com.example.renteco_v1.rental_history.data.Ride
import com.example.renteco_v1.rental_history.data.RideRepository
import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import com.example.renteco_v1.rental_items.data.model.ParkingZone
import com.example.renteco_v1.rental_items.data.repo.ParkingZoneRepository
import com.example.renteco_v1.rental_items.data.repo.VehicleRepository
import com.example.renteco_v1.user_access.login.data.AuthRepository
import com.example.renteco_v1.user_access.login.data.remote.User
import kotlinx.coroutines.launch

class RentingViewModel(
    private val vehicleId: Int?,
    private val vehicleRepository: VehicleRepository,
    private val rideRepository: RideRepository,
    private val parkingZoneRepository: ParkingZoneRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    var uiState: VehicleUiState by mutableStateOf(VehicleUiState(loadResult = Result.Loading))
    var uiRentStatus: Int by mutableStateOf<Int>(0)
    var uiParkingZoneState by mutableStateOf<ParkingZone?>(null)


    init {
        loadVehicle()
    }

    private fun loadVehicle() {

        viewModelScope.launch {
            vehicleRepository.refresh()
            vehicleRepository.itemStream.collect { vehicles ->
                if (!(uiState.loadResult is Result.Loading)) {
                    return@collect
                }
                val vehicle = vehicles.find({ it.id == vehicleId }) ?: AutoVehicle()
                uiState = uiState.copy(vehicle = vehicle, loadResult = Result.Success(vehicle))
            }
        }
    }

    public fun updateVehicle(
        vehicle: AutoVehicle,
        boolean: Boolean,
        location: LocationData?,
        street: String
    ): Boolean {

        viewModelScope.launch {
            authRepository.updateUser(Api.currentUser.copy(inrent = 0))  //update user renting status
            Log.d("Tag", "Brand vehicle:${vehicle.brand}")
            vehicle.rented = boolean
            vehicle.address = street
            vehicle.latitude = location?.latitude ?: 0.0
            vehicle.longitude = location?.longitude ?: 0.0
            val autovehicle = vehicleRepository.updateVehicle(vehicle.id, vehicle)
            Log.d("tag", "Locatie: ${vehicle.latitude} ${vehicle.longitude}")
            if (autovehicle != null) {
                uiRentStatus = 1//poate fi finalizata inchirierea
            } else {
                Api.currentUser = Api.currentUser.copy(inrent = vehicleId!!)
                uiRentStatus = 2//eroare
            }
            // return@launch able//daca a fost modificat este true

        }
        return false

    }


    fun openCar(context: Context, vehicleId: Int?) {
        val phoneNumber = "0790588159"
        val message = generateMessage("Open")
        // Dacă permisiunea este acordată, trimite SMS-ul
        sendSMS(context, phoneNumber, message)
    }

    private fun sendSMS(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "SMS trimis cu succes!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Eroare la trimiterea SMS-ului: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

    fun closeCar(context: Context, vehicleId: Int?) {
        val phoneNumber = "0790588159"
        val message = generateMessage("Close")
        // Dacă permisiunea este acordată, trimite SMS-ul
        sendSMS(context, phoneNumber, message)
    }

    fun addRide(
        endTime: String,
        oldStreet: String,
        currentStreet: String,
        price: Int,
        idUser: Int,
        idVehicle: Int
    ) {
        val ride = Ride(
            0, oldStreet, currentStreet, Api.startTime, endTime, price,
            User(idUser),
            AutoVehicle(idVehicle)
        )
        viewModelScope.launch {
            rideRepository.addRide(ride)
        }
    }

    fun checkParkingZone(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            uiParkingZoneState =
                parkingZoneRepository.findParkingZone(ParkingZone(0, "", latitude, longitude))
        }
    }

    companion object { //daca are companion si object inseamna ca e singleton
        fun Factory(vehicleId: Int?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                RentingViewModel(
                    vehicleId,
                    app.container.vehicleRepository,
                    app.container.rideRepository,
                    app.container.parkingZoneRepository,
                    app.container.authRepository
                )
            }
        }
    }
}