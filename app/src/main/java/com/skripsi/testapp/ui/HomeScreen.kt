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
                    colorStops = arrayOf(
                        0f to Color(0xFF1FB362), // Start color (white)
                        0.6f to Color(0xFF1FB362),// Duplicate the first color to stop at 60%
                        1f to Color(0xFF61F878)  // End color (green)
                    ),
                    start = Offset(0f, 0f),                  // Top
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(1.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pic1),
                                contentDescription = "split leaf",
                                Modifier.size(67.dp, 82.dp),
                                alignment = Alignment.Center
                            )
                            Text(
                                text = "Ambil daun",
                                fontSize = 8.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(67.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pic2),
                                contentDescription = "Scanner",
                                Modifier.size(85.dp),
                                alignment = Alignment.Center
                            )
                            Text(
                                text = "Scan seladamu",
                                fontSize = 8.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(80.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pic3),
                                contentDescription = "diagnosis",
                                Modifier.size(92.dp, 90.dp)
                            )
                            Text(
                                text = "Lihat hasil",
                                fontSize = 8.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(80.dp)
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(1.dp))
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            ScanButton(onImageCaptured = {}, navController = navController)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    // Aksi untuk berpindah ke ChatActivity
                    context.startActivity(Intent(context, ChatActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF61F878),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Buka Chat")
            }
        }
    }

}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}