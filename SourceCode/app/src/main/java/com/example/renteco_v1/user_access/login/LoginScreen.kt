package com.example.renteco_v1.user_access.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.renteco_v1.ui.theme.RentEco_V1Theme
import com.example.renteco_v1.user_access.LoadImageFromAssets

@Composable
fun LoginScreen(onClose: () -> Unit, onRegisterNow: () -> Unit) {

    val loginViewModel = viewModel<LoginViewModel>(factory = LoginViewModel.Factory)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginUiState = loginViewModel.uiState


    Column {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .padding((8.dp))
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            LoadImageFromAssets("images/rent.png")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "RentEco",
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            TextField(
                label = { Text(text = "Email") },
                value = email,
                onValueChange = {
                    email = it
                })
            Spacer(modifier = Modifier.size(4.dp))
            TextField(
                label = { Text(text = "Password") },
                value = password,
                onValueChange = {
                    password = it
                })

            Button(
                onClick = { loginViewModel.login(email, password) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Login", fontSize = 16.sp)

            }
            TextButton(
                onClick = { onRegisterNow() },
            ) {
                Text(
                    text = "still don't have an account.Register now!",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            if (loginUiState.isAuthenticating == true) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                );
            }
            if (loginUiState.authenticationError != null && loginUiState.authenticationCompleted == false) {
                if (!loginUiState.authenticationError?.message!!.startsWith("failed to connect to")) {
                    Text(
                        "Email or password incorect. Please retry!",
                        modifier = Modifier.padding(2.dp)
                    )
                    Log.d(TAG, "err login ${loginUiState.authenticationError.message.toString()}")
                } else {
                    Log.d(TAG, "Server may not work at this moment, please try again later")
                    Text(
                        "Server may not work at this moment, try later!",
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }

    }


    LaunchedEffect(loginUiState.authenticationCompleted) {
        Log.d(TAG, "Auth completed");
        if (loginUiState.authenticationCompleted) {
            onClose();
        }
    }
}

