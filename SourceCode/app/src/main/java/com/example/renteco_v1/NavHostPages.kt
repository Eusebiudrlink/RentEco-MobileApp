package com.example.renteco_v1

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.renteco_v1.account.ui.AccountView
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.contact.ContactView
import com.example.renteco_v1.features.localPreferences.UserPreferences
import com.example.renteco_v1.features.network.WaitingScreen
import com.example.renteco_v1.rental_history.ui.RidesView
import com.example.renteco_v1.rental_items.ui.renting.RentingScreen
import com.example.renteco_v1.rental_items.ui.vehicle.VehicleScreen
import com.example.renteco_v1.rental_items.ui.vehicles.VehicleListView
import com.example.renteco_v1.user_access.StartPage
import com.example.renteco_v1.user_access.login.LoginScreen
import com.example.renteco_v1.user_access.registration.RegistrationScreen

sealed class Screen(val route: String) {

    object StartPage : Screen("startpage")
    object Account : Screen("account")
    object VehicleListScreen : Screen("vehiclelistscreen")
    object LoginScreen : Screen("loginscreen")
    object VehicleScreen : Screen("vehiclescreen")
    object RentingScreen : Screen("rentingscreen")
    object RegistrationScreen : Screen("registerscreen")
    object RidesScreen : Screen("ridesscreen")
    object ContactScreen : Screen("contactscreen")
    object WaitingScreen : Screen("waitingscreen")

}

@Composable
fun setupNavHost(navController: NavHostController, userPreferencesUiState: UserPreferences, myAppViewModel: RentEcoViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.StartPage.route
    ) {

        composable(route = "${Screen.StartPage.route}")
        {
            StartPage(navigateToRegister = {
                Log.d(ContentValues.TAG, "StartPage screen closing. Navigate")
                navController.navigate(Screen.RegistrationScreen.route)
            }, navigateToLogin = {
                navController.navigate(Screen.LoginScreen.route)
            })
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(onClose = {
                Log.d(ContentValues.TAG, "Login screen closing. Navigate")
                navController.navigate(Screen.VehicleListScreen.route) { popUpTo(0) }
            }, onRegisterNow = {
                Log.d(ContentValues.TAG, "Login screen closing. Navigate to Register screen`")
                navController.navigate(Screen.RegistrationScreen.route)
            })
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(onClose = {
                Log.d(ContentValues.TAG, "Register screen closing. Navigate")
                navController.navigate(Screen.LoginScreen.route)
            })
        }
        composable(route = Screen.Account.route) {
            AccountView(onClose = {
                Log.d(ContentValues.TAG, "AccountView screen closing. Navigate")
                navController.navigate(Screen.VehicleListScreen.route)
            })
        }
        composable(route = Screen.RidesScreen.route) {
            RidesView(onRideSelected = { idRide ->
                Log.d(ContentValues.TAG, "Selected ride:${idRide}")
            }, onClose = {
                navController.navigate(Screen.VehicleListScreen.route) { popUpTo(0) }
            })
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(onClose = {
                Log.d(ContentValues.TAG, "Register screen closing. Navigate")
                navController.navigate(Screen.LoginScreen.route)
            })
        }
        composable(route = Screen.VehicleListScreen.route) {
            VehicleListView(onVehicleSelect = { id ->
                Log.d(ContentValues.TAG, "Navigate to vehiclescreen from list:${id}")
                navController.navigate("${Screen.VehicleScreen.route}/$id")
            }, newNavSelected = {
                navController.navigate(it)
            }, alreadyRenting = {
                navController.navigate("${Screen.RentingScreen.route}/${Api.currentUser.inrent}/${Api.startTime}")
            },
                onLogOut = {
                    Log.d("RentEcoNavHost", "logout")
                    myAppViewModel.logout()
                    Api.tokenInterceptor.token = null
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(0)
                    }
                })
        }
        composable(
            route = "${Screen.VehicleScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            Log.d(ContentValues.TAG, "vehiclescreen cu id :${id}")
            val idVehicle = it.arguments?.getInt("id") ?: 1
            VehicleScreen(vehicleId = idVehicle, onRentClick = { id, startTime ->
                Log.d("Navhost", " renting a car ")
                navController.navigate("${Screen.RentingScreen.route}/$id/$startTime")
            },
                onUnavailableVehicle = {
                    navController.navigate(Screen.VehicleListScreen.route)
                })
        }
        composable(route = "${Screen.RentingScreen.route}/{id}/{startTime}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("startTime") { type = NavType.StringType }
            )
        ) {
            val idVehicle = it.arguments?.getInt("id") ?: 1

            RentingScreen(idVehicle = idVehicle, onEndTrip = {
                Log.d("Navhost", "End trip")
                navController.navigate(Screen.VehicleListScreen.route)
            })
        }

        composable(route = Screen.ContactScreen.route) {
            ContactView(onClose = {
                Log.d(ContentValues.TAG, "AccountView screen closing. Navigate")
                navController.navigate(Screen.VehicleListScreen.route)
            })
        }
        composable(route = "${Screen.WaitingScreen.route}") {
            WaitingScreen()
        }
    }
}
