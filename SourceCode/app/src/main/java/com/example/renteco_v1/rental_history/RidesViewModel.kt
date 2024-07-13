package com.example.renteco_v1.rental_history

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.rental_history.data.Ride
import com.example.renteco_v1.rental_history.data.RideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RidesViewModel(
    private val rideRepository: RideRepository
) : ViewModel() {

    val uiState: Flow<List<Ride>> = rideRepository.itemStream

    init {
        Log.d(ContentValues.TAG, "init RidesViewmodel")
        loadItems()
    }

    private fun loadItems() {
        Log.d(ContentValues.TAG, "load items")
        viewModelScope.launch {
            rideRepository.refresh()//asa lansez o corutina
        }
    }

    companion object { //daca are companion si object inseamna ca e singleton
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                RidesViewModel(app.container.rideRepository)
            }
        }
    }
}
