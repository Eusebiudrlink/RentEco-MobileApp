package com.example.renteco_v1.user_access.registration

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onClose: () -> Unit
) {
    val registrationViewModel =
        viewModel<RegistrationViewModel>(factory = RegistrationViewModel.Factory)


    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val dateState = rememberDatePickerState()
    var checkedState by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var textDialog by remember { mutableStateOf("") }
    var verificationComplete = false
    val registerUiState = registrationViewModel.uiState

    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val textResultUiState = registrationViewModel.textResult

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectedImage = it
        }
    var context = LocalContext.current

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Welcome to RentEco! ", fontSize = 24.sp)
        Text("Please continue registration below by entering your necessary details.")
        Spacer(modifier = Modifier.padding(8.dp))

        TextField(label = { Text(text = "Full Name") }, value = name, onValueChange = {
            name = it
        })
        Spacer(modifier = Modifier.padding(8.dp))


        TextField(label = { Text(text = "Email") }, value = email, onValueChange = {
            email = it
        })
        Spacer(modifier = Modifier.padding(8.dp))

        TextField(label = { Text(text = "Password") }, value = password, onValueChange = {
            password = it
        })
        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            label = { Text(text = "Birth date Ex:12.06.2002") },
            value = date,
            onValueChange = {
                date = it
            })
        Spacer(modifier = Modifier.padding(8.dp))

        TextField(label = { Text(text = "Address") }, value = address, onValueChange = {
            address = it
        })
        Spacer(modifier = Modifier.padding(2.dp))


        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
        ) {
            Text(text = "Pick image with Driver License from Gallery ")
        }

        if (selectedImage != null) {
            AsyncImage(
                model = selectedImage,
                contentDescription = "test",
                modifier = Modifier
                    .size(50.dp) // Specificați dimensiunea dorită a imaginii
                //.clip(shape = RoundedCornerShape(4.dp))
            )
            LaunchedEffect(selectedImage) {
                //Afisati imaginea selectata
                val imageUrl = RealPathUtil.getRealPath(context, selectedImage!!)
                registrationViewModel.getOcrResult(context, imageUrl!!)
            }
            if (textResultUiState.value != "") {
                if (registrationViewModel.checkIfLicenseIsValid(textResultUiState.value) && registrationViewModel.compareInfoWithPhoto(
                        textResultUiState.value, name, date
                    )
                ) {
                    verificationComplete = true
                    Text("The picture entered corresponds to your data!", fontSize = 8.sp)
                } else
                    Text(
                        "The picture does not seem to correspond to the entered data! Please retry!.",
                        fontSize = 8.sp
                    )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top=16.dp,bottom=16.dp)
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { isChecked ->
                    checkedState = isChecked
                }
            )
            Spacer(modifier = Modifier.width(8.dp)) // Spațiu între checkbox și text
            Text(
                "For the purpose of data processing, the user's consent is required. Please check the box to agree to the terms and conditions!",
                fontSize = 8.sp
            )
        }

        Button(
            onClick = {
                if (verificationComplete) {
                    if (checkedState) {
                        registrationViewModel.register(email, password, name, date, address)
                        onClose();
                    } else {
                        textDialog = "Check and read the terms and conditions"
                        showDialog = true
                    }
                } else {
                    textDialog = "Wait to check the driver license!"
                    showDialog = true
                }

            }, modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Register", fontSize = 24.sp)

        }
        if (showDialog == true) {
            AlertDialog(

                title = {
                    Text(text = "Atentie")
                },
                text = {
                    Text(text = textDialog)
                },
                onDismissRequest = {
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                    registrationViewModel.removeErrorState()
                },

                )
        }
        if (registerUiState.isAuthenticating) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            );
        }
        if (registerUiState.authenticationError != null) {
            textDialog =
                registerUiState.authenticationError.message ?: "Error, retry and check all fields"
            showDialog = true;
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegistrationScreen({})
}

object RealPathUtil {
    fun getRealPath(context: Context, uri: Uri): String? {
        return try {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            val path = columnIndex?.let { cursor.getString(it) }
            cursor?.close()
            path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

