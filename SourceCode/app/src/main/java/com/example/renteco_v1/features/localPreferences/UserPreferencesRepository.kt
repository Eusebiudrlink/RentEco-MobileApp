package com.example.renteco_v1.features.localPreferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val email = stringPreferencesKey("email")
        val token = stringPreferencesKey("token")
        val startTimeRent = stringPreferencesKey("startTimeRent")
    }


    val userPreferencesStream: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { mapUserPreferences(it) }

    suspend fun savePreferences(userPreferences: UserPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.email] = userPreferences.email
            preferences[PreferencesKeys.token] = userPreferences.token
            preferences[PreferencesKeys.startTimeRent] = userPreferences.startTimeRent
        }
    }

    private fun mapUserPreferences(preferences: Preferences) =
        UserPreferences(
            email = preferences[PreferencesKeys.email]  ?: "",
            token = preferences[PreferencesKeys.token]  ?: "",
            startTimeRent = preferences[PreferencesKeys.startTimeRent]  ?: ""
        )
    }