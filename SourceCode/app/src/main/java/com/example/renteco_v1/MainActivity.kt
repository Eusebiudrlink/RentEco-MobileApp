package com.example.renteco_v1

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.ui.theme.RentEco_V1Theme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentEco_V1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RentEcoNavHost()
                }
            }
        }
    }
}
