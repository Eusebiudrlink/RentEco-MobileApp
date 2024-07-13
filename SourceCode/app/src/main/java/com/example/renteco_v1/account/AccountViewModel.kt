package com.example.renteco_v1.account

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.features.location.LocationData
import com.example.renteco_v1.user_access.login.data.AuthRepository
import com.example.renteco_v1.user_access.login.data.remote.User
import com.example.renteco_v1.user_access.registration.RegistrationViewModel
import kotlinx.coroutines.launch

class AccountViewModel (
        private val authRepository: AuthRepository
    ) : ViewModel() {

    val _updatedUserSuccesfull = mutableStateOf<User>(User())
    val updatedUserSuccesfull: State<User> = _updatedUserSuccesfull

    fun updateUser(user: User) {
        viewModelScope.launch {
            Log.d(ContentValues.TAG, "updateUser")
            _updatedUserSuccesfull.value= authRepository.updateUser(user)?.copy()?:User()
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            Log.d(ContentValues.TAG, "Factory account view model")
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                AccountViewModel(app.container.authRepository)
            }
        }
    }
    }
