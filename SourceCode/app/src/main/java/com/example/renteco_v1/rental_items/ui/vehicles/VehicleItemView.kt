package com.example.renteco_v1.rental_items.ui.vehicles

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.renteco_v1.rental_items.data.model.AutoVehicle

@Composable
fun VehicleDetail(vehicle: AutoVehicle, navigateToVehicle: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, bottom = 2.dp)
            .clickable { navigateToVehicle(vehicle.id) }.border(width=1.dp, color = Color(0xFF646566)),
    ) {
        Surface( modifier = Modifier,
            color = Color(0xFFCCCCCC)
        ) {
            Column(modifier = Modifier) {
                AsyncImage(
                    model = vehicle.linkimg,
                    contentDescription = vehicle.brand,
                    placeholder =
                        // Placeholder
                        ColorPainter(color = Color.LightGray)
                    ,
                    modifier = Modifier
                        .size(150.dp)
                    //.clip(shape = RoundedCornerShape(4.dp))

                )
            }
            }
        Column(
            modifier = Modifier
                .padding(16.dp,16.dp,8.dp,16.dp),
        ) {
            Text(
                " ${vehicle.brand}", fontSize = 23.sp,
                //textAlign = TextAlign.End,modifier = Modifier.fillMaxWidth()
            )
            Text(
                "Model: ${vehicle.model}", fontSize = 16.sp,
                //   textAlign = TextAlign.End,modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(24.dp))
            Surface(
                modifier = Modifier.shadow(8.dp),
            ) {
                Text(
                    "Price: ${vehicle.price} Lei/h",
                    fontSize = 22.sp,
                    color = Color(0xFFE57373), // sau orice altă culoare vibrantă
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}