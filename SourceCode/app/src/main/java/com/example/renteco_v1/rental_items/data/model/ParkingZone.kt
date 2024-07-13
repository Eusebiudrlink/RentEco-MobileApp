package com.example.renteco_v1.rental_items.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ParkingZone")
data class ParkingZone(
    @PrimaryKey val id: Int = 0, val name:String="",
    var latitude: Double = 0.0, var longitude: Double = 0.0
)