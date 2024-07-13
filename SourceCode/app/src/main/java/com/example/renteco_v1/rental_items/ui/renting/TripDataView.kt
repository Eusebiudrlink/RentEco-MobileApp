package com.example.renteco_v1.rental_items.ui.renting

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.features.location.LocationData
import com.example.renteco_v1.rental_items.viewmodel.RentingViewModel
import com.example.renteco_v1.rental_items.data.model.AutoVehicle
import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant

@Composable
fun showTripData(
    rentingviewModel: RentingViewModel,
    vehicle: AutoVehicle,
    price: Int,
    oldStreet: String,
    currentStreet: String,
    location: LocationData?,
    onEndTrip: () -> Unit
) {
    val endTime = Clock.System.now()
    Log.d(ContentValues.TAG, "Start time : ${Api.startTime}")
    val duration = endTime - (Api.startTime.toInstant())
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    val totalPrice = hours * price + (minutes * price) / 60 + 10
    Log.d("Tag", "Strada: $currentStreet")


    Box(
        modifier = Modifier.run { fillMaxSize().background(color = Color.Black.copy(alpha = 0.4f)) },
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),

            ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Felicitări! Ai finalizat cursa!\n" +
                            "Durata este: $hours ore și $minutes minute\n" +
                            "Pret Total:${totalPrice}",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    Api.currentUser = Api.currentUser.copy(inrent = 0)
                    rentingviewModel.updateVehicle(vehicle, false, location, currentStreet)
                    rentingviewModel.addRide(
                        endTime.toString(),
                        oldStreet,
                        currentStreet,
                        price,
                        Api.currentUser.id,
                        vehicle.id
                    )
                    Log.d("tag","End trip cu curentuser.inrent${Api.currentUser.inrent}")
                    onEndTrip()
                }) {
                    Text("Okey")
                }
            }
        }
    }

}


