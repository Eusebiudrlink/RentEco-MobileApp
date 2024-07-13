package com.example.renteco_v1.user_access.login.data.remote

data class User(
    val id:Int=0,
    val email: String="",
    val user_password: String="",
    val full_name:String="",
    val birth_date:String="",
    val user_address:String="",
    val inrent:Int=0
)
