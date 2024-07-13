package com.example.renteco_v1.rental_items.ui.vehicle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.renteco_v1.features.location.LocationData
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LocationTab(
    location: LocationData
) {
    val userLocation = remember {
        mutableStateOf(LatLng(location.latitude, location.longitude))
    }
    var cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(userLocation.value, 10f)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            cameraPositionState = cameraPositionState,

            ) {
            Marker(state = MarkerState(position = userLocation.value))
        }

    }
}

