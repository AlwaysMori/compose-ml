package com.skripsi.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.skripsi.testapp.ui.theme.skripsiTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.Manifest
import android.widget.Toast
import com.skripsi.testapp.ui.Navigation
import com.skripsi.testapp.ui.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val PERMISSION_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            skripsiTheme {
                AppStart()
            }
        }
    }

    @Composable
    fun AppStart() {
        val isSplashScreenVisible = remember { mutableStateOf(true) }

        // Control splash screen display duration
        LaunchedEffect(Unit) {
            delay(4000)  // Show splash screen for 4 seconds
            isSplashScreenVisible.value = false
        }

        AnimatedVisibility(
            visible = isSplashScreenVisible.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SplashScreen()
        }

        AnimatedVisibility(
            visible = !isSplashScreenVisible.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            // Request permissions at runtime
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this@MainActivity, permissions, PERMISSION_REQUEST_CODE)
            Navigation()  // Navigate to your main screen after splash
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // All permissions are granted
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permissions are denied
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    skripsiTheme {
        SplashScreen()
    }
}
