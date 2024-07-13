package com.example.renteco_v1.user_access.login.data.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.renteco_v1.api.Api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

class AuthDataSource() {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/api/auth/login")
        suspend fun login(@Body user: User): TokenHolder
        @Headers("Content-Type: application/json")
        @POST("/api/auth/register")
        suspend fun register(@Body user: User):Boolean
        @Headers("Content-Type: application/json")
        @POST("/api/auth/update")
        suspend fun updateUser(@Header("Authorization") authorization: String,@Body user: User):User
        @Headers("Content-Type: application/json")
        @GET("/api/auth/user")
        suspend fun getUser(@Header("Authorization") authorization: String,@Query("email") email: String):User
    }

    private val authService: AuthService = Api.retrofit.create(AuthService::class.java)

    suspend fun login(user: User): Result<TokenHolder> {
        try {
            Log.v(TAG,"login AuthDataSource")

            return Result.success(authService.login(user))
        } catch (e: Exception) {
            Log.w(TAG, "login failed", e)
            return Result.failure(e)
        }
    }

    suspend fun register(user: User): Result<Boolean> {
        try {
            Log.v(TAG,"register AuthDataSource")
            return Result.success(authService.register(user))
        } catch (e: Exception) {
            Log.w(TAG, "register failed", e)
            return Result.failure(e)
        }
    }
    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun getUser(email: String): User {
        try {
            Log.v(TAG,"register AuthDataSource")
            return authService.getUser(authorization = getBearerToken(),email)
        } catch (e: Exception) {
            Log.w(TAG, "Getting user failed", e)
             return User()
        }

    }

   suspend  fun updateUser(user: User, authorization: String): User? {

        try {
            Log.v(TAG,"updateUser AuthDataSource")
            return authService.updateUser(authorization = getBearerToken(),user)
        } catch (e: Exception) {
            Log.w(TAG, "update user failed", e)
            return User()
        }
    }
}

