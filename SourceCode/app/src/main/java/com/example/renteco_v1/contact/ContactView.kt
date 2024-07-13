package com.example.renteco_v1.contact

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.renteco_v1.MainActivity
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.features.location.LocationUtils
import com.example.renteco_v1.rental_items.viewmodel.LocationViewModel
import com.example.renteco_v1.rental_items.viewmodel.VehicleListViewModel
import com.example.renteco_v1.user_access.LoadImageFromAssets

@Composable
fun ContactView(onClose: () -> Unit) {
    val contactViewModel= viewModel<ContactViewModel>()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val locationViewModel = viewModel<LocationViewModel>(factory = LocationViewModel.Factory)
    var loadingLocation = remember { mutableStateOf(false) }
    val location = locationViewModel.location.value


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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            LoadImageFromAssets("images/contact_help.png")
        }
        Text(
            text = "Contact us",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "For questions, sugestions, complains or any other issues feel free to contact us",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.Black, shape = MaterialTheme.shapes.medium)
        ) {
            Text(
                text = "Email: eusebiu.damatar@stud.ubbcluj.ro",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold, // Font mai gros pentru evidențiere
                    color = Color(0xFF3F51B5) // O culoare diferită pentru a se distinge
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "If emergency or problems:",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.Black, shape = MaterialTheme.shapes.medium)
        ) {

            Text(
                text = "Telefon: +40 759 511 881",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold, // Font mai gros pentru evidențiere
                    color = Color(0xFFD32F2F) // O culoare diferită pentru a se distinge
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Button(onClick = {
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
        }) {
            Text(text = "Send request for help")
        }

        if (location != null) {
            contactViewModel.requestHelp(context,Api.currentUser.inrent,location.latitude,location.longitude, Api.currentUser.email)
            Text(
                text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(8.dp)
            )

        }

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Button(
                onClick = { onClose() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(text = "Back", fontSize = 16.sp)
            }
        }


    }

}