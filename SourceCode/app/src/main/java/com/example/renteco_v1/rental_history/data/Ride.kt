package com.example.renteco_v1.rental_history.data

import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import com.example.renteco_v1.user_access.login.data.remote.User

data class Ride(
    val id: Int,
    val fromAddress: String,
    val toAddress: String,
    val startTime: String,
    val endTime: String,
    val price: Int,
    val userId: User,
    val vehicleId: AutoVehicle
)