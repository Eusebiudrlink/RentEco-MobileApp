package com.example.renteco_v1.rental_items.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import kotlinx.coroutines.flow.Flow
@Dao

interface VehicleDao
{
    @Query("SELECT * FROM AutoVehicles")
    fun getAll(): Flow<List<AutoVehicle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: AutoVehicle)

    @Query("DELETE FROM AutoVehicles")
    suspend fun deleteAll()
}