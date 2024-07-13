package com.example.renteco_v1.rental_items.ui.vehicle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.renteco_v1.rental_items.data.model.AutoVehicle

@Composable
fun VehicleDetailsTab(autoVehicle: AutoVehicle) {

    Surface(
    ) {
        Column() {
            Text(text = "Vehicle Details", fontSize = 24.sp, fontWeight = FontWeight.Bold)


                Row() {
                    Column() {
                        Text(
                            "Brand & Model:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                    }
                    Column() {
                        Text(
                            " ${autoVehicle.brand} ${autoVehicle.model}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                    }
                }
                Row() {
                    Column() {
                        Text(
                            "Type:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                    }
                    Column(Modifier.padding(start=80.dp)) {
                        Text(
                            " electric",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                    }
                }
                Row() {
                    Column() {
                        Text(
                            "Capacity:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                    }
                    Column {
                        Text(
                            " ${autoVehicle.capacity} ",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 48.dp)
                        )

                    }
                }
                Row() {
                    Column() {
                        Text(
                            "Address:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                    }
                    Column {
                        Text(
                            " ${autoVehicle.address}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 52.dp)
                        )

                    }
                }
                Row() {
                    Column() {
                        Text(
                            "Price/hour:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                    }
                    Column {
                        Text(
                            " ${autoVehicle.price} Lei",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp, start = 32.dp)
                        )

                    }
                }

            }

    }
}
