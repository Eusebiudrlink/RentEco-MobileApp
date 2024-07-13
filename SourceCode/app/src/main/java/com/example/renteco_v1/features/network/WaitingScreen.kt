package com.example.renteco_v1.features.network

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.renteco_v1.user_access.LoadImageFromAssets

@Composable
fun WaitingScreen(){
    Box(
        modifier = Modifier.run { fillMaxSize().background(color = Color.Black.copy(alpha = 0.4f)) },
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(16.dp),

            ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadImageFromAssets("images/no_network.jpg")
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Check your network connection!", fontSize = 24.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Try to connect to another source of internet!", fontSize = 24.sp, textAlign = TextAlign.Center)
            }
        }


    }
}
@Composable
fun NetworkStatusSnackbar(
    networkStatus: Boolean,
    onDismiss: () -> Unit
) {
    if (!networkStatus) {

            Box(modifier = Modifier.fillMaxWidth().padding(4.dp),contentAlignment = Alignment.TopCenter) {
                Snackbar(
                    modifier = Modifier.padding(top = 4.dp),
                    action = {
                        TextButton(onClick = onDismiss) {
                            Text("Dismiss")
                        }
                    },
                ) {
                    Text("Check your network connexion!")
                }
            }
    }
}