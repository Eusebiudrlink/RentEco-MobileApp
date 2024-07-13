package com.example.renteco_v1.rental_history.data.remote

import com.example.renteco_v1.rental_history.data.Ride
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RideService {
    @GET("/api/rides/")
    suspend fun find(@Header("Authorization") authorization: String): List<Ride>

//    @Headers("Content-Type: application/json")
//    @PUT("/api/rides/{id}")
//    suspend fun update(
//        @Header("Authorization") authorization: String,
//        @Path("id") id: Int,
//        @Body receivedVehicle: AutoVehicle
//    ): AutoVehicle
//    @Headers("Content-Type: application/json")
    @POST("/api/rides/")
    suspend fun addRide(@Header("Authorization") authorization: String,@Body ride: Ride):Boolean
}