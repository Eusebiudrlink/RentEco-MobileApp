package com.example.renteco_v1.rental_items.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.features.location.GeocodingResult
import com.example.renteco_v1.features.location.LocationData
import com.example.renteco_v1.features.location.RetrofitClient
import kotlinx.coroutines.launch

class LocationViewModel: ViewModel() {
    private  val _location= mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address


    fun updateLocation(newLocation: LocationData){
        _location.value = newLocation
        Log.d(ContentValues.TAG,"A facut update in location model")
        Log.d(ContentValues.TAG,"Locatia viewmodel: ${location}" )

    }

    fun fetchAddress(latlng: String){
        try{
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    "A8******3sQ"
                )
                Log.d(ContentValues.TAG,"Locatia Addressa  viewmodel: ${address.value.firstOrNull()?.formatted_address?: "No Address"}")

                _address.value = result.results
            }
        }catch(e:Exception) {
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }
    companion object { //daca are companion si object inseamna ca e singleton
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                LocationViewModel()
            }
        }
    }

}