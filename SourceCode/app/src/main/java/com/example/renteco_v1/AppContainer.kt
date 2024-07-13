package com.example.renteco_v1

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.example.renteco_v1.features.localPreferences.UserPreferencesRepository
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.rental_history.data.RideRepository
import com.example.renteco_v1.rental_history.data.remote.RideService
import com.example.renteco_v1.rental_items.data.repo.ParkingZoneRepository
import com.example.renteco_v1.user_access.login.data.AuthRepository
import com.example.renteco_v1.user_access.login.data.remote.AuthDataSource
import com.example.renteco_v1.rental_items.data.repo.VehicleRepository
import com.example.renteco_v1.rental_items.data.remote.ParkingZoneService
import com.example.renteco_v1.rental_items.data.remote.VehicleService

val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)
class AppContainer(val context: Context) {

    init {
        Log.d(TAG, "init")
    }


    private val vehicleService = Api.retrofit.create(VehicleService::class.java)
    private val rideService = Api.retrofit.create(RideService::class.java)
    private val parkingZoneService = Api.retrofit.create(ParkingZoneService::class.java)

    val vehicleRepository: VehicleRepository by lazy {
        VehicleRepository(vehicleService)
    }
    val rideRepository:RideRepository by lazy {
        RideRepository(rideService)
    }
    val parkingZoneRepository: ParkingZoneRepository by lazy {
        ParkingZoneRepository(parkingZoneService)
    }

    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.userPreferencesDataStore)
    }
    private val authDataSource: AuthDataSource = AuthDataSource()
    val authRepository: AuthRepository by lazy {
        AuthRepository(authDataSource)
    }


}
