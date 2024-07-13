package com.example.renteco_v1.rental_items.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AutoVehicles")
data class AutoVehicle(
    @PrimaryKey val id: Int = 0, val brand: String = "",
    val model: String = "", var rented: Boolean = true, val linkimg: String = "",
    val price: Int = 0, val capacity: Int = 0, val number:String="",
    var latitude: Double = 0.0, var longitude: Double = 0.0, var address: String = "",
    val sim: String = "07"
)
