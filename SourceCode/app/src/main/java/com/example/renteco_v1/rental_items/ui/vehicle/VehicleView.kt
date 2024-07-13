package com.example.renteco_v1.rental_items.ui.vehicle

import kotlinx.datetime.*

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myapp.todo.features.tabs.MyTab
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.features.location.LocationData
import com.example.renteco_v1.features.location.LocationUtils
import com.example.renteco_v1.rental_items.viewmodel.LocationViewModel
import com.example.renteco_v1.rental_items.viewmodel.VehicleViewModel

private enum class TabPage {
    Detalii, Locatie
}

@Composable
private fun HomeTabBar(
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        contentColor = Color(0xFF00668B),
    ) {
        MyTab(
            icon = Icons.Default.Info,
            title = "Details",
            onClick = { onTabSelected(TabPage.Detalii) }
        )
        MyTab(
            icon = Icons.Default.Send,
            title = "Location",
            onClick = { onTabSelected(TabPage.Locatie) }
        )
    }
}



@Composable
fun VehicleScreen(vehicleId: Int, onRentClick: (Int, String) -> Unit,onUnavailableVehicle:()->Unit) {

    val vehicleViewModel =
        viewModel<VehicleViewModel>(factory = VehicleViewModel.Factory(vehicleId))
    var vehicleUiState = vehicleViewModel.uiState
    var rentStatus = vehicleViewModel.uiRentStatus

    val locationViewModel = viewModel<LocationViewModel>(factory = LocationViewModel.Factory)
    val context = LocalContext.current

    val locationUtils = LocationUtils(context)

    var tabPage by remember { mutableStateOf(TabPage.Detalii) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                // I HAVE ACCESS to location
                locationUtils.requestLocationUpdates(viewModel = locationViewModel)
            } else {
                Toast.makeText(
                    context,
                    "Location Permission is required. Please enable it in the Android Settings",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Surface( modifier = Modifier,
            color = Color(0xFFCCCCCC)
        ) {
            AsyncImage(
                model = vehicleUiState.vehicle.linkimg,
                contentDescription = vehicleUiState.vehicle.brand,
                modifier = Modifier
                    .fillMaxWidth(),
                //.clip(shape = RoundedCornerShape(4.dp))
            )

        }
        HomeTabBar(
            tabPage = tabPage,
            onTabSelected = { tabPage = it }
        )

        if (tabPage == TabPage.Detalii) {
            VehicleDetailsTab(vehicleUiState.vehicle)
        } else {//locatia
            LocationTab(
                location = LocationData(
                    vehicleUiState.vehicle.latitude,
                    vehicleUiState.vehicle.longitude
                )
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            Button(
                onClick = {

                    vehicleViewModel.updateVehicleStatus(vehicleUiState.vehicle, true)
                },
                modifier = Modifier.size(width = 200.dp, height = 50.dp)
            )
            {
                Text("Rent")
            }
            if (rentStatus == 1) {
                onRentClick(vehicleUiState.vehicle.id, Api.startTime)
            } else if (rentStatus == 2) {
                Toast.makeText(
                    context,
                    "Sorry.The car is unavailable at this moment!",
                    Toast.LENGTH_LONG
                ).show()
                rentStatus==0
                onUnavailableVehicle()
            }

        }
    }

}


