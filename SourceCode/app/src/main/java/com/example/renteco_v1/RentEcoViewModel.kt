package com.example.renteco_v1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.features.localPreferences.UserPreferences
import com.example.renteco_v1.features.localPreferences.UserPreferencesRepository
import kotlinx.coroutines.launch

class RentEcoViewModel( private val userPreferencesRepository: UserPreferencesRepository):ViewModel() {
    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.savePreferences(UserPreferences())
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                RentEcoViewModel(
                    app.container.userPreferencesRepository,

                )
            }
        }
    }
}