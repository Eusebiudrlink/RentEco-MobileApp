package com.example.renteco_v1.rental_items.ui.renting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.renteco_v1.features.location.LocationData
import com.example.renteco_v1.rental_items.viewmodel.RentingViewModel
import com.example.renteco_v1.rental_items.viewmodel.VehicleUiState
import com.example.renteco_v1.user_access.LoadImageFromAssets

@Composable
fun toPayment(
    vehicleViewModel: RentingViewModel,
    vehicleUiState: VehicleUiState,
    price: Int,
    oldVehicleAddress: State<String>,
    newVehicleAddress: State<String>, location: LocationData?,
    onEndTrip: () -> Unit
) {

    val paymentSuccessfully = remember { mutableStateOf(false) }
    if (paymentSuccessfully.value) {
        showTripData(
            vehicleViewModel,
            vehicleUiState.vehicle,
            vehicleUiState.vehicle.price,
            oldVehicleAddress.value,
            newVehicleAddress.value, location,
            onEndTrip = onEndTrip
        )
    } else {
        paymentView(onPaymentComplete = { paymentSuccessfully.value = true })
    }

}

@Composable
fun paymentView(onPaymentComplete: () -> Unit) {
    var cardNumber by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.run { fillMaxSize().background(color = Color.Black.copy(alpha = 0.4f)) },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .size(100.dp)
                    .padding(top = 16.dp, end = 16.dp)
            ) {
                LoadImageFromAssets("images/visa.png")
            }
            Text("Card number", modifier = Modifier.padding(start = 16.dp))
            TextField(
                label = { Text(text = "4140 4970 **** ****") },
                value = cardNumber,
                onValueChange = {
                    cardNumber = it
                }, modifier = Modifier.padding(start = 16.dp)
            )

            Row(modifier = Modifier.padding(top = 8.dp)) {
                Column(
                    modifier = Modifier
                        .padding(end = 48.dp),
                ) {
                    Text("Expiration date", modifier = Modifier.padding(start = 16.dp))
                    TextField(
                        label = { Text(text = "05/11") },
                        value = cardNumber,
                        onValueChange = {
                            cardNumber = it
                        }, modifier = Modifier
                            .padding(start = 16.dp)
                            .width(100.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(end = 32.dp),

                    ) {
                    Text("CVV", modifier = Modifier.padding(start = 16.dp))
                    TextField(
                        label = { Text(text = "888") },
                        value = cardNumber,
                        onValueChange = {
                            cardNumber = it
                        }, modifier = Modifier
                            .padding(start = 16.dp)
                            .width(80.dp)
                    )
                }
            }

            Text("Full name", modifier = Modifier.padding(start = 16.dp, top = 8.dp))
            TextField(
                label = { Text(text = "Max Verstappen") },
                value = cardNumber,
                onValueChange = {
                    cardNumber = it
                }, modifier = Modifier.padding(start = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onPaymentComplete() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Pay")
                }
            }
        }
    }
}
