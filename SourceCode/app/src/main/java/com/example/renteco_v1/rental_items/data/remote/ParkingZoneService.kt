package com.example.renteco_v1.rental_items.data.remote

import com.example.renteco_v1.rental_items.data.model.ParkingZone
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ParkingZoneService {
    @Headers("Content-Type: application/json")
    @POST("/api/parkingzone/findLocation")
    suspend fun findParkingZone(@Header("Authorization") authorization: String,@Body parkingZone: ParkingZone): ParkingZone


}