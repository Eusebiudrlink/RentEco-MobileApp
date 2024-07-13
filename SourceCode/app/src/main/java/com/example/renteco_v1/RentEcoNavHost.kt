package com.example.renteco_v1

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapp.core.ui.UserPreferencesViewModel
import com.example.renteco_v1.features.localPreferences.UserPreferences
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.features.popUps.ElectricPopups
import com.example.renteco_v1.features.network.NetworkStatusSnackbar
import com.example.renteco_v1.features.network.NetworkStatusViewModel
import com.example.renteco_v1.features.popUps.ElectricAdvantagesList
import kotlinx.coroutines.delay
import java.util.Date


@Composable
fun RentEcoNavHost() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val electricAdvantagesList = remember { ElectricAdvantagesList(context) }

    val userPreferencesViewModel =
        viewModel<UserPreferencesViewModel>(factory = UserPreferencesViewModel.Factory)
    val userPreferencesUiState by userPreferencesViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = UserPreferences()
    )
    val myNetworkStatusViewModel = viewModel<NetworkStatusViewModel>(
        factory = NetworkStatusViewModel.Factory(
            LocalContext.current.applicationContext as Application
        )
    )
    val networkStatus = myNetworkStatusViewModel.uiState
    var popupVisible by remember { mutableStateOf(false) }
    var delayForPopUp = 180*1000L //3 minute
    var popupCount by remember { mutableStateOf(0) }
    var lastResetTime by remember { mutableStateOf(Date().time) }

    fun controlPopUp() {
        popupVisible = true
    }
    LaunchedEffect(Unit) {
        while (true) {
            // Afișează pop-up-ul la fiecare delayForPopUp minute
            delay(delayForPopUp)
            val currentTime = Date().time
            // Resetează contorul după 24 de ore
            if (currentTime - lastResetTime > 24 * 60 * 60 * 1000) {
                popupCount = 0
                lastResetTime = currentTime
            }
            if (popupCount < 3) {
                if(navController.currentDestination?.route != Screen.Account.route &&
                    navController.currentDestination?.route != Screen.ContactScreen.route&&
                    navController.currentDestination?.route != Screen.RentingScreen.route&&
                    navController.currentDestination?.route != Screen.LoginScreen.route&&
                    navController.currentDestination?.route != Screen.RegistrationScreen.route) {
                    Log.d("RentEcoNavHost", "Show popup:${popupCount}")
                    popupVisible = true
                    popupCount++
                }
            }
        }
    }

    val myAppViewModel = viewModel<RentEcoViewModel>(factory = RentEcoViewModel.Factory)

    Column {
        if (networkStatus == false) {
            Log.d("RentEcoNavHost", "Network is offline")
            NetworkStatusSnackbar(networkStatus = networkStatus) {
            }
        } else {
            Log.d("RentEcoNavHost", "Network is online")
        }
        if (popupVisible) {

                ElectricPopups(electricAdvantagesList.getElectricAdvantages(), onClosePopup = {
                    popupVisible = false
                    electricAdvantagesList.stopTracking()
                    delayForPopUp = electricAdvantagesList.getNewDelay(delayForPopUp)
                    Log.d("RentEcoNavHost", "New delay for popup: $delayForPopUp")
                })


        }
        setupNavHost(navController, userPreferencesUiState, myAppViewModel)

    }
    LaunchedEffect(userPreferencesUiState.token) {
        if (userPreferencesUiState.token.isNotEmpty()) {
            Log.d("MyAppNavHost", "Lauched effect navigate to items")
            Log.d("Tag", "token: ${userPreferencesUiState.token}")
            Api.tokenInterceptor.token = userPreferencesUiState.token
            Api.currentEmail = userPreferencesUiState.email
            Api.startTime= userPreferencesUiState.startTimeRent
            navController.navigate(Screen.VehicleListScreen.route) {
                popUpTo(0)//cu asta nu mai las user ul sa dea back
            }
        }
    }

}


