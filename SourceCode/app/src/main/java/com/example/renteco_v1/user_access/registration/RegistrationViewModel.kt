package com.example.renteco_v1.user_access.registration

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteco_v1.MyApplication
import com.example.renteco_v1.features.location.LocationData
import com.example.renteco_v1.user_access.login.LoginUiState
import com.example.renteco_v1.user_access.login.data.AuthRepository
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.cloud.vision.v1.ImageAnnotatorSettings

import com.google.protobuf.ByteString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

data class RegisterUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: Throwable? = null,
    val authenticationCompleted: Boolean = false,
)


class RegistrationViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _textResult = mutableStateOf<String>("")
    val textResult: State<String> = _textResult
    var uiState: RegisterUiState by mutableStateOf(RegisterUiState())


    fun getOcrResult(context: Context, imageUrl: String) {

        Log.d(TAG, "Path to image: $imageUrl")

        // Load the image
        val imageBytes = ByteString.copyFrom(File(imageUrl).readBytes())
        val image = Image.newBuilder().setContent(imageBytes).build()
        val scope =
            CoroutineScope(Dispatchers.IO) // Definește un CoroutineScope care rulează pe thread-ul IO
        val credentials =
            GoogleCredentials.fromStream(context.assets.open("peppy-tiger-427814-d4-b3045159644b.json"))
        scope.launch {
            // Create an ImageAnnotatorClient without explicit credentials
            val client = ImageAnnotatorClient.create(
                ImageAnnotatorSettings.newBuilder().setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)
                ).build()
            )
            try {
                // Perform text detection
                val feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build()
                val request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feature)
                    .setImage(image)
                    .build()

                val response = client.batchAnnotateImages(listOf(request))

                // Process the response
                val textAnnotations = response.responsesList[0].textAnnotationsList
                if (textAnnotations.isNotEmpty()) {
                    println("Detected text:")
                    _textResult.value = textAnnotations.toString()
                }
            } finally {
                // Close the client
                client.close()
            }

        }


    }

    fun checkIfLicenseIsValid(value: String): Boolean {
        //verific ca data e valabila si ca este categoria B
      return true
    }

    fun compareInfoWithPhoto(convertedText: String, name: String, date: String): Boolean {
        val formattedText = convertedText.replace("\\n", "\n")
        Log.d("Tag","Converted text:${formattedText}")

        val SecondNameConverted = Regex("1\\..*?\\n").find(formattedText)?.value
        val FirstNameConverted = Regex("2\\..*?\\n").find(formattedText)?.value
        Log.d("Tag","Converted SecondName:${SecondNameConverted}")
        Log.d("Tag","Converted SecondName:${FirstNameConverted}")

        val dateConverted=Regex("3\\. [0-2][0-9]\\.[0-1][0-9]\\.[1-2][0-9]{3}").find(convertedText)?.value?.substring(3)
        val nameConverted =
            SecondNameConverted?.substring(3,SecondNameConverted.length-1) +" "+ FirstNameConverted?.substring(3,FirstNameConverted.length-1)
        Log.d("Tag","Converted name:${nameConverted} and name :${name}")

        Log.d("Tag","Converted date:${dateConverted} and date : ${date}")

        if (nameConverted == name && dateConverted==date)
            return true
        return false
    }

    fun register(email: String, password: String, name: String, date: String, address: String) {
        viewModelScope.launch {
            Log.v(TAG,"register user..")
            uiState=uiState.copy(isAuthenticating = true,authenticationError = null)
            val result=authRepository.register(email,password,name,date,address)
            if(result.isSuccess){
               // uiState=uiState.copy(isAuthenticating = false, authenticationCompleted = true)
                Log.v(TAG,"register WITH SUCCESS")
                uiState=uiState.copy(isAuthenticating = false, authenticationCompleted = true)
            }
            else
            {
                uiState=uiState.copy(isAuthenticating = false,authenticationError = result.exceptionOrNull())
             //   uiState=uiState.copy(isAuthenticating = false,authenticationError = result.exceptionOrNull())
            }
        }
    }

    fun removeErrorState() {
        uiState=uiState.copy(isAuthenticating = false,authenticationError = null)
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            Log.d(ContentValues.TAG, "Factory registration view model")
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                RegistrationViewModel(app.container.authRepository)
            }
        }
    }
}