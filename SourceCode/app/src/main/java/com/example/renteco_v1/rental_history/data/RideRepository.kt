package com.example.renteco_v1.rental_history.data

import android.content.ContentValues
import android.util.Log
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.rental_history.data.remote.RideService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class RideRepository(
    private val rideService:RideService
) {

    var itemStream = flow {
        while (true) {
            val latest = items
            emit(latest)
            delay(100)
        }
    }
    var items = listOf<Ride>()

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun refresh() {
        Log.d(ContentValues.TAG, "refresh started")
        try {
            //val items = vehicleService.find(authorization = getBearerToken())
            items = rideService.find(authorization = getBearerToken())
            Log.d(ContentValues.TAG, "primele 2 rides gasite ${items[1]}${items[2]}   ")

            //vehicleDao.deleteAll()
            //items.forEach { vehicleDao.insert(it) }
            Log.d(ContentValues.TAG, "refresh succeeded")

        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "refresh failed", e)
        }
    }

    suspend fun addRide(ride: Ride) {
            rideService.addRide(authorization = getBearerToken(),ride)
    }
}