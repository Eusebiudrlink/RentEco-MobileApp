package com.example.renteco_v1.features.popUps

import android.content.Context
import android.os.SystemClock
import java.io.BufferedReader
import java.io.InputStreamReader

class ElectricAdvantagesList(val context: Context) {

    private val userBehavior = UserBehavior(context)
    private var messages: List<String> = emptyList()
    private var currentIndex = 0
    private var startTime: Long = 0
    private var lastAverage: Long = 0
     init {
         messages = readMessagesFromFile("electricAdvantages.txt")
     }

     private fun readMessagesFromFile(fileName: String): List<String> {
         val messages = mutableListOf<String>()
         try {
             context.assets.open(fileName).use { inputStream ->
                 BufferedReader(InputStreamReader(inputStream)).use { reader ->
                     var line = reader.readLine()
                     while (line != null) {
                         messages.add(line)
                         line = reader.readLine()
                     }
                 }
             }
         } catch (e: Exception) {
             println("Error reading file: ${e.message}")
         }
         return messages
     }

    fun getElectricAdvantages():String{
        startTracking()
        if(currentIndex==messages.size ){
            currentIndex=0
        }
        return messages[currentIndex++]
    }
    private fun startTracking() {
        startTime = SystemClock.elapsedRealtime()
    }
    fun stopTracking() {
        val timeSpent= SystemClock.elapsedRealtime() - startTime
        lastAverage = userBehavior.getAverageTimeSpent()
        userBehavior.recordTimeSpent(timeSpent)
    }
    fun getNewDelay(delayForPopUp: Long): Long {
        val newAverage = userBehavior.getAverageTimeSpent()
        if (newAverage > lastAverage && delayForPopUp < 120*1000L) { // nu mai putin de 2 minute
            return (delayForPopUp * 0.8).toLong()
        } else {
            if (delayForPopUp < 480*1000)//nu mai mult de 8 minute
               return (delayForPopUp * 1.2).toLong()
        }
        return delayForPopUp
    }


}

