package com.example.renteco_v1.rental_items.data.repo

import android.content.ContentValues.TAG
import android.util.Log
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import com.example.renteco_v1.rental_items.data.local.VehicleDao
import com.example.renteco_v1.rental_items.data.remote.VehicleService
//import com.example.renteco_v1.rental_items.data.remote.VehicleWsClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf


class VehicleRepository(
    private val vehicleService: VehicleService
) {
    //val itemStream by lazy {vehicleDao.getAll()}
    var itemStream = flow {
        while (true) {
            val latest = items
            emit(latest)
            delay(100)
        }
    }
    var items = listOf<AutoVehicle>()
    //  var itemStream= listOf<AutoVehicle>(AutoVehicle(1,"test","test",true))

    init {
        Log.d(TAG, "Vehicle repository created")
    }

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun refresh() {
        Log.d(TAG, "refresh started")
        try {
            //val items = vehicleService.find(authorization = getBearerToken())
            items = vehicleService.find(authorization = getBearerToken())

            //items.forEach { vehicleDao.insert(it) }
            Log.d(TAG, "refresh succeeded")

        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
        }
    }

 suspend fun updateVehicle(id: Int, vehicle: AutoVehicle) : AutoVehicle?{
        try {
            Log.d("VehicleRepository", "update vehicle with id=${id}")
            val updatedVehicle =
                vehicleService.update(
                    id = id,
                    receivedVehicle = vehicle,
                    authorization = getBearerToken()
                )

            return updatedVehicle
        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
            return null;
        }

    }
}