package com.example.renteco_v1.rental_items.data.repo

import android.content.ContentValues.TAG
import android.util.Log
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.rental_items.data.model.ParkingZone
import com.example.renteco_v1.rental_items.data.remote.ParkingZoneService
//import com.example.renteco_v1.rental_items.data.remote.VehicleWsClient


class ParkingZoneRepository(
    private val parkingZoneService: ParkingZoneService,
) {

    init {
        Log.d(TAG, "ParkingZone repository created")
    }

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun findParkingZone(parkingZone: ParkingZone): ParkingZone {
        try {
            Log.d(TAG, "find parking zone")
            return parkingZoneService.findParkingZone(authorization = getBearerToken(), parkingZone)
        } catch (e: Exception) {
            Log.w(TAG, "find parking zone failed", e)
            return ParkingZone()
        }
    }

}