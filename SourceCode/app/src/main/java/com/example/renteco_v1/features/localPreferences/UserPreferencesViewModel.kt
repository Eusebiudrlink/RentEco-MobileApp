package com.example.myapp.core.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.features.localPreferences.UserPreferences
import com.example.renteco_v1.features.localPreferences.UserPreferencesRepository
import com.example.renteco_v1.MyApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserPreferencesViewModel(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {
    val uiState: Flow<UserPreferences> = userPreferencesRepository.userPreferencesStream

    init {
        Log.d("TAG", "init")
    }

    fun save(userPreferences: UserPreferences) {
        viewModelScope.launch {
            Log.d("TAG", "saveUserPreferences...");
            userPreferencesRepository.savePreferences(userPreferences)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                UserPreferencesViewModel(app.container.userPreferencesRepository)
            }
        }
    }
}

