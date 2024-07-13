package com.example.renteco_v1.user_access.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.features.localPreferences.UserPreferences
import com.example.renteco_v1.features.localPreferences.UserPreferencesRepository
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.user_access.login.data.AuthRepository
import kotlinx.coroutines.launch

data class LoginUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: Throwable? = null,
    val authenticationCompleted: Boolean = false,
    val token: String = ""
)

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var uiState: LoginUiState by mutableStateOf(LoginUiState())

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Log.v(TAG, "login..")
            uiState = uiState.copy(isAuthenticating = true, authenticationError = null)
            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                userPreferencesRepository.savePreferences(
                    UserPreferences(
                        email,
                        result.getOrNull()?.token ?: ""
                    )
                )
                Log.d("LoginScreen","login succesful")
                uiState = uiState.copy(isAuthenticating = false, authenticationCompleted = true)
            } else {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = result.exceptionOrNull()
                )
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            Log.d(TAG, "A ajuns in factory view model")
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                LoginViewModel(
                    app.container.authRepository,
                    app.container.userPreferencesRepository
                )
            }
        }
    }


}