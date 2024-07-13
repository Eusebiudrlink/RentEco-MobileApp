package com.example.renteco_v1.rental_history.ui

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.renteco_v1.rental_history.RidesViewModel


@Composable
fun RidesView(onRideSelected: (Int) -> Unit, onClose: () -> Unit) {

    val listViewModel = viewModel<RidesViewModel>(factory = RidesViewModel.Factory)
    val sItems by listViewModel.uiState.collectAsStateWithLifecycle(initialValue = listOf())

    Surface {

        Row(
            horizontalArrangement = Arrangement.Start,
        ) {

            IconButton(onClick = { onClose() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

        Log.d(ContentValues.TAG, "Recompose RidesView ${sItems.size}")

        LazyColumn(
            modifier = Modifier
                .padding(top = 32.dp, start = 32.dp)
        ) {
            item{
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Rides history",
                        modifier = Modifier.padding(8.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                }

            }
            items(sItems) { item ->
                RideItemView(ride = item, onRideSelected)
            }
        }
    }

}