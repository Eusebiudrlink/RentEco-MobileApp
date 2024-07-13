package com.example.renteco_v1.rental_items.data.remote

import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface VehicleService {
    @GET("/api/item/")
    suspend fun find(@Header("Authorization") authorization: String): List<AutoVehicle>

    @Headers("Content-Type: application/json")
    @PUT("/api/item/{id}")
    suspend fun update(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Body receivedVehicle: AutoVehicle
    ): AutoVehicle
}