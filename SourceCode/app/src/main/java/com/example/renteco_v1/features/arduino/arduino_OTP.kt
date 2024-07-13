package com.example.renteco_v1.features.arduino

import com.example.renteco_v1.api.Api
import java.security.MessageDigest
import java.util.Random

fun generateOTP(): String {
    val random = Random()
    val otp = StringBuilder()
    for (i in 0 until 6) {
        otp.append(random.nextInt(10))
    }
    return otp.toString()
}

fun getCurrentTimestamp(): Long {
    System.out.println(System.currentTimeMillis() / 1000)
    return System.currentTimeMillis() / 1000
}

fun generateHash(otp: String, timestamp: Long, secretKey: String): String {
    val dataToHash = "$otp$timestamp$secretKey"
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(dataToHash.toByteArray())
    return hash.joinToString("") { "%02x".format(it) }
}

fun generateMessage(message:String): String {
    val secretKey = Api.secretKey
    val otp = generateOTP()
    val timestamp = getCurrentTimestamp()
    val hash = generateHash(otp, timestamp, secretKey)
    return "$message:$otp:$timestamp:$hash"
}


