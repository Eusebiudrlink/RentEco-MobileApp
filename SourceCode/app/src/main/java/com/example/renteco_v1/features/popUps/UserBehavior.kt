package com.example.renteco_v1.features.popUps

import android.content.Context

class UserBehavior(val context: Context) {
    private val preferences = context.getSharedPreferences("UserBehavior", Context.MODE_PRIVATE)

    fun recordTimeSpent(timeSpent: Long) {
        val times = preferences.getLong("totalTimeSpent", 0)
        val views = preferences.getInt("viewsCount", 0)
        preferences.edit().putLong("totalTimeSpent", times + timeSpent).apply()
        preferences.edit().putInt("viewsCount", views + 1).apply()
    }

    fun getAverageTimeSpent(): Long {
        val times = preferences.getLong("totalTimeSpent", 0)
        val views = preferences.getInt("viewsCount", 0)
        return if (views > 0) times / views else 0
    }

}