package com.example.renteco_v1.account.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.renteco_v1.account.AccountViewModel
import com.example.renteco_v1.api.Api
import com.example.renteco_v1.user_access.LoadImageFromAssets
import com.example.renteco_v1.user_access.login.data.remote.User
import kotlinx.coroutines.delay

@Composable
fun AccountView(onClose: () -> Unit) {
    val accountViewModel =
        viewModel<AccountViewModel>(factory = AccountViewModel.Factory)
    val showEditData = remember { mutableStateOf("") }
    val updateState = accountViewModel.updatedUserSuccesfull
    var updatedUser = remember { mutableStateOf(Api.currentUser) }

    Surface( modifier = Modifier.fillMaxSize(),
        color = Color(0xFFCCCCCC)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally).padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                LoadImageFromAssets("images/account1.jpeg")
            }
            Text(
                text = "Account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold, // Textul bold
                color = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Name: ${Api.currentUser.full_name}",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(12.dp)
            )
            Text(
                text = "Birth Date: ${Api.currentUser.birth_date}",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(12.dp)
            )
            Row {
                Text(
                    text = "Email: ${Api.currentUser.email}",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(12.dp)
                )
                TextButton(onClick = { showEditData.value = "email" }) {
                    Text("Edit")
                }
            }

            Row {
                Text(
                    text = "Password: ***********",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(12.dp)
                )
                TextButton(onClick = { showEditData.value = "password" }) {
                    Text("Edit")
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onClose() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Back", fontSize = 16.sp)
            }

//

        }
        if (showEditData.value == "email") {
            editData("email", accountViewModel) {
                showEditData.value = ""
                updatedUser.value = it
            }
        }
        if (showEditData.value == "password") {
            editData("password", accountViewModel) {
                showEditData.value = ""
                updatedUser.value = it
            }
        }
        if (updateState.value.id != 0) {
            Log.d("AccountView", "User updated successfully!")
            Api.currentUser = updatedUser.value
            Log.d("AccountView", "email updated ${Api.currentUser.email}!")
            Popup(message = "User updated successfully!", durationMillis = 3000)
        } else {
            Log.d("AccountView", "User update failed!")
            //  Popup(message = "User update failed!", durationMillis = 3000)
        }
    }
}

@Composable
fun Popup(message: String, durationMillis: Long = 3000) {
    var visible by remember { mutableStateOf(true) }
    Log.d("AccountView", "Popup test")
    LaunchedEffect(visible) {
        delay(durationMillis)
        visible = false
    }

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(56.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(8.dp),
                color = Color.Black
            )
        }
    }
}

@Composable
fun editData(
    dataType: String,
    accountViewModel: AccountViewModel,
    onClose: (User) -> Unit
): Boolean {

    var email by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }

    var updatedUser: User = User(0, "", "", "", "", "")

    Box(
        modifier = Modifier.run { fillMaxSize().background(color = Color.Black.copy(alpha = 0.4f)) },
        contentAlignment = Alignment.Center
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
                if (dataType == "email") {
                    TextField(
                        label = { Text(text = "email") },
                        value = email,
                        onValueChange = {
                            email = it
                        })
                    updatedUser = Api.currentUser.copy(email = email)

                } else {
                    TextField(
                        label = { Text(text = "password") },
                        value = password1,
                        onValueChange = {
                            password1 = it
                        }, visualTransformation = PasswordVisualTransformation()
                    )
                    TextField(
                        label = { Text(text = "password") },
                        value = password1,
                        onValueChange = {
                            password1 = it
                        }, visualTransformation = PasswordVisualTransformation()
                    )
                    if (password1 == password2)
                        updatedUser = Api.currentUser.copy(user_password = password1)
                    else {
                        Text("Passwords do not match")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    Log.d("AccountView", "update user")
                    accountViewModel.updateUser(updatedUser)
                    onClose(updatedUser)
                }) {
                    Text("Save")
                }
            }
        }

    }
    return true
}
