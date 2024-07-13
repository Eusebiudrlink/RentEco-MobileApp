package com.example.renteco_v1.contact

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
import androidx.lifecycle.ViewModel

class ContactViewModel: ViewModel(){

    private fun sendSMS(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "SMS trimis cu succes!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Eroare la trimiterea SMS-ului: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun requestHelp(context: Context, vehicleId: Int?,latitude: Double, longitude: Double, email: String) {
        val phoneNumber = "0790588159"
        val message = "Help needed at: $latitude, $longitude. Email: $email and vehicle id: $vehicleId"

        // Dacă permisiunea este acordată, trimite SMS-ul
        sendSMS(context,phoneNumber, message)

    }

}