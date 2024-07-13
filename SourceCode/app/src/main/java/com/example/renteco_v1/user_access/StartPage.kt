package com.example.renteco_v1.user_access

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadImageFromAssets(imageAsset:String) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        val assetManager = context.assets
        val inputStream = assetManager.open(imageAsset)
        bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
        inputStream.close()
    }

    if (bitmap != null) {
        Image(bitmap = bitmap!!, contentDescription = "Real life usage")
    }
}
@Composable
fun StartPage(navigateToRegister: () -> Unit, navigateToLogin: () -> Unit) {


    Column( modifier = Modifier
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        LoadImageFromAssets("images/rent.png")
        Spacer(modifier = Modifier.height(100.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { navigateToRegister() },
                modifier = Modifier.size(width = 180.dp, height = 58.dp),
            ) {
                Text("Register", fontSize = 24.sp)
            }
            Spacer(modifier=Modifier.width(5.dp))
            Button(
                onClick = { navigateToLogin() },
                modifier = Modifier.size(width = 180.dp, height = 58.dp)
            ) {
                Text("Login", fontSize = 24.sp)
            }
        }

    }
}