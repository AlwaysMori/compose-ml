package com.skripsi.testapp.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.skripsi.testapp.R

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6EC1E4), // Soft blue
                        Color(0xFF56E39F)  // Soft green
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // Placeholder Image
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .size(400.dp)
                    .padding(bottom = 16.dp)
            )

            ScanButton(onImageCaptured = {}, navController = navController)
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        context.startActivity(Intent(context, ChatActivity::class.java))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7FFF4E),
                        contentColor = Color.Black
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                ) {
                    Text(
                        text = "Buka Chat",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        // Navigate to DetailListScreen
                        navController.navigate("")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7FFF4E),
                        contentColor = Color.Black
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                ) {
                    Text(
                        text = "Detail Penyakit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
