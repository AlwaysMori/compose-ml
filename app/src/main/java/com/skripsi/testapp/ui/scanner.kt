package com.skripsi.testapp.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.skripsi.testapp.R
import com.skripsi.testapp.classifyImage
import java.io.File

@Composable

fun ScanButton(onImageCaptured: (Uri?) -> Unit, navController: NavHostController) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val classificationResults = remember { mutableStateOf<FloatArray?>(null) }

    if (showDialog.value) {
        ImagePicker(onImageCaptured = { uri ->
            uri?.let {
                bitmap.value = createBitmapFromUri(context, it)
                bitmap.value?.let { bmp ->
                    if (classificationResults.value == null) {
                        classificationResults.value = classifyImage(context, bmp)
                        Log.d("ScanButton", "Classification results: ${classificationResults.value}")
                        navController.navigate(
                            "result/${Uri.encode(it.toString())}/${classificationResults.value?.joinToString(",") ?: ""}"
                        )
                    }
                }
            }
            showDialog.value = false
        }, onDismiss = { showDialog.value = false })
    }

    Button(
        onClick = {
            showDialog.value = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF61F878),
            contentColor = Color.Black
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = "Identifikasi Penyakit",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp)             )

            Image(
                painter = painterResource(id = R.drawable.pic_diagnose),
                contentDescription = "diagnose",
                modifier = Modifier.size(50.dp, 50.dp)
            )
        }
    }
}



@Composable
fun ImagePicker(onImageCaptured: (Uri?) -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        Log.d("ImagePicker", "Selected URI: $uri")
        onImageCaptured(uri)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
        if (success) {
            Log.d("ImagePicker", "Captured URI: ${imageUri.value}")
            onImageCaptured(imageUri.value)
        } else {
            onDismiss()
        }
    }

    chooseImageSource(context, onOptionSelected = { option ->
        when (option) {
            "Gallery" -> galleryLauncher.launch("image/*")
            "Camera" -> {
                val uri = createImageUri(context)
                imageUri.value = uri
                cameraLauncher.launch(uri)
            }
        }
    }, onDismiss = onDismiss)
}

@Composable
fun chooseImageSource(context: Context, onOptionSelected: (String) -> Unit, onDismiss: () -> Unit) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                onDismiss()
            },
            title = { Text(text = "Pilih sumber gambar", color = Color.Black) },
            text = { Text("Gunakan Galeri atau Kamera untuk mendeteksi penyakit", color = Color.Black) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onOptionSelected("Gallery")
                }) {
                    Text("Galeri", color = Color(0xFF61AF2B))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onOptionSelected("Camera")
                }) {
                    Text("Kamera", color = Color(0xFF61AF2B))
                }
            },
            containerColor = Color.White
        )
    }
}

fun createImageUri(context: Context): Uri {
    val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val image = File(imagesDir, "image.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", image)
}


fun createBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val originalBitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()

    Log.d("createBitmapFromUri", "Bitmap: $originalBitmap")
    return originalBitmap
}
