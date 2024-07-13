package com.example.renteco_v1.rental_history.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.renteco_v1.rental_history.data.Ride
import com.example.renteco_v1.features.getDuration
import com.example.renteco_v1.features.getMonth
import com.example.renteco_v1.user_access.LoadImageFromAssets

@Composable
fun RideItemView(ride: Ride, navigateToRide: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .height(70.dp)
                .width(70.dp)
        ) {
            LoadImageFromAssets("images/logo-rent.jpeg")
        }
        Column() {
            val month: String = getMonth(ride.startTime)
            val duration: String = getDuration(ride.startTime, ride.endTime)

            Text("Month: $month")
            Text(
                text = buildAnnotatedString {
                    append("Car: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${ride.vehicleId.brand} ${ride.vehicleId.model}")
                    }
                },
            )

            Text("Time: ${duration}")

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Bottom)
                .padding(end = 1.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End

        ) {
            Text(
                text = buildAnnotatedString {
                    append("Price: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${ride.price}lei")
                    }
                },
            )
        }

    }

}






