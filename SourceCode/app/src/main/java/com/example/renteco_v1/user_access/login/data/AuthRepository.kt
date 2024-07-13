package com.example.renteco_v1.user_access.login.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.user_access.login.data.remote.AuthDataSource
import com.example.renteco_v1.user_access.login.data.remote.TokenHolder
import com.example.renteco_v1.user_access.login.data.remote.User

class AuthRepository(private val authDataSource: AuthDataSource) {
    init {
        Log.d(TAG, "init")
    }

    fun clearToken() {
        Api.tokenInterceptor.token = null
    }
    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun login(email: String, password: String): Result<TokenHolder> {
        val user = User(email=email, user_password = password)
        val result = authDataSource.login(user)
        Log.d(TAG, "login result: $result")
        if (result.isSuccess) {
            Api.tokenInterceptor.token = result.getOrNull()?.token
        }
        else{
            Api.tokenInterceptor.token = null
        }
        return result
    }

   suspend fun register(email: String, password: String, name: String, date: String, address: String): Result<Boolean> {
       val user = User(0,email,password,name,date,address,0)
       val result = authDataSource.register(user)
       return result
    }

    suspend fun getUser(email: String): User {
    return authDataSource.getUser(email)
    }
    suspend fun updateUser(user: User) : User?{
        try {
            Log.d("VehicleRepository", "update User with id=${user.id} and email=${user.email}")
            val updateUser =
                authDataSource.updateUser(
                    user = user,
                    authorization = getBearerToken()
                )

            return updateUser
        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
            return null;
        }

    }
}
