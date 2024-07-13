package com.example.renteco_v1.rental_items.ui.renting

import android.Manifest
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.renteco_v1.MainActivity
import com.example.renteco_v1.R
import com.example.renteco_v1.features.location.LocationUtils
import com.example.renteco_v1.rental_items.viewmodel.LocationViewModel
import com.example.renteco_v1.rental_items.viewmodel.RentingViewModel

@Composable
fun RentingScreen(
    idVehicle: Int,
    onEndTrip: () -> Unit,
) {
    val vehicleViewModel =
        viewModel<RentingViewModel>(factory = RentingViewModel.Factory(vehicleId = idVehicle))

    var vehicleUiState = vehicleViewModel.uiState
    val context = LocalContext.current
    var bottomSheetVisible by remember { mutableStateOf(false) }
    var waitingForParkingZoneResponse by remember { mutableStateOf(false) }
    val parkingZoneUiState = vehicleViewModel.uiParkingZoneState
    val locationUtils = LocationUtils(context)
    val locationViewModel = viewModel<LocationViewModel>(factory = LocationViewModel.Factory)
    var loadingLocation = remember { mutableStateOf(false) }
    val location = locationViewModel.location.value
    val address = locationViewModel.address.value
    val fetchingAddress = remember { mutableStateOf(false) }
    var oldVehicleAddress = remember { mutableStateOf("") }
    var newVehicleAddress = remember { mutableStateOf("") }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                // I HAVE ACCESS to location
                locationUtils.requestLocationUpdates(viewModel = locationViewModel)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired) {
                    Toast.makeText(
                        context,
                        "Location Permission is required for this feature to work",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Location Permission is required. Please enable it in the Android Settings",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        })

    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = vehicleUiState.vehicle.linkimg,
            contentDescription = vehicleUiState.vehicle.brand,
            modifier = Modifier
                .padding(4.dp)
                .size(width = 300.dp, height = 250.dp),
            //.clip(shape = RoundedCornerShape(4.dp))
        )

        Text(
            text = "License plate: ${vehicleUiState.vehicle.number}",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 4.dp),
            fontSize = 24.sp
        )

        Button(modifier = Modifier.padding(bottom = 4.dp), onClick = {
            openGoogleMaps(
                context,
                vehicleUiState.vehicle.latitude,
                vehicleUiState.vehicle.longitude
            )
        }) {
            Text("Last Location")
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { vehicleViewModel.closeCar(context, vehicleUiState.vehicleId) },
                modifier = Modifier.size(width = 180.dp, height = 48.dp)
            ) {
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.lockclose),
                        contentDescription = "Close",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text("Close")

            }
            Button(
                onClick = { vehicleViewModel.openCar(context, vehicleUiState.vehicleId) },
                modifier = Modifier.size(width = 180.dp, height = 48.dp)
            ) {
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.lockopen),
                        contentDescription = "Open",
                        modifier = Modifier.size(24.dp)
                    )

                }
                Text("Open")

            }
        }

        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.Center) {
            val imagePainter: Painter = painterResource(id = R.drawable.chargingicon)
            Image(
                painter = imagePainter,
                contentDescription = "Charging icon",
                modifier = Modifier
                    .size(56.dp)
                    .padding(top = 8.dp)
            )
            TextButton(
                onClick = { openGoogleMapsChargingStations(context) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Find charging stations")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "1.The key is inside the car.",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                "2.Do not overspeed!",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                "3.Keep the car clean.",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text("4.Enjoy!", fontSize = 24.sp, modifier = Modifier.padding(vertical = 4.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {
                    if (locationUtils.hasLocationPermission(context)) {
                        locationUtils.requestLocationUpdates(locationViewModel)
                        loadingLocation.value = true
                    } else {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                    //opresc cursa, salvez ultima locatie si calculez timpul
                },
                modifier = Modifier.size(width = 200.dp, height = 48.dp)
            ) {
                Text("END TRIP")
            }

        }
    }
    if (waitingForParkingZoneResponse == false)
        if (loadingLocation.value == true) {
            if (location != null) {

                if (fetchingAddress.value == false) {
                    //Text("Address:${location.latitude} and ${location.longitude}\n ") //$address")
                    locationViewModel.fetchAddress("${location.latitude},${location.longitude}")
                    Log.d("TAG", "Locatia Addressa VIEW:${address.firstOrNull()?.formatted_address}")
                    fetchingAddress.value = true
                }
                if (address.firstOrNull()?.formatted_address ?: "No Address" != "No Address") {
                    //poti sa dai end la cursa doar daca ai permisiuni de locatie active
                    loadingLocation.value = false
                    newVehicleAddress.value =
                        address.firstOrNull()?.formatted_address?.substringBefore(",")
                            ?: "No Address"

                    Log.d(TAG, "Locatia Addressa VIEW:${newVehicleAddress.value}")
                    oldVehicleAddress.value =
                        vehicleUiState.vehicle.address.substringBefore(",") ?: "No Address"

                    vehicleViewModel.checkParkingZone(location.latitude, location.longitude)
                    waitingForParkingZoneResponse = true
                    Log.d("TAG", "Waiting for parking zone response")

                } else {
                    CircularProgressIndicator()
                }
            } else {
                CircularProgressIndicator()
            }

        }

  //  if (waitingForParkingZoneResponse == true && parkingZoneUiState != null) { cod modificat ca sa poti parca oriunde
        if (waitingForParkingZoneResponse == true && (parkingZoneUiState != null||parkingZoneUiState==null)) {
//            if (parkingZoneUiState.id != 0)//sigur nu se transforma in null inapoi
//        {
            bottomSheetVisible = true
//           parkingZoneUiState.name?.let { Log.d(TAG, "Zona in care ati parcat este:$it") }
//        } else {
//            Toast.makeText(
//                context,
//                "You are not in the perimeter of the parking area",
//                Toast.LENGTH_LONG
//            ).show()
//        }
    }

    if (bottomSheetVisible == true) {
        toPayment(
            vehicleViewModel,
            vehicleUiState,
            vehicleUiState.vehicle.price,
            oldVehicleAddress,
            newVehicleAddress,
            location,
            onEndTrip = onEndTrip
        )
    }


}


