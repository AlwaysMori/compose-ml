package com.skripsi.testapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HelpScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF6EC1E4), Color(0xFF56E39F)),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, 0f)
                            )
                        )
                        .padding(12.dp),
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = com.skripsi.testapp.R.drawable.baseline_close_24),
                            contentDescription = "exit",
                            modifier = Modifier
                                .size(35.dp)
                                .clickable(onClick = {
                                    navController.navigate("home") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                })
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Help",
                            color = Color.Black,
                            fontWeight = Bold,
                            fontSize = 24.sp,

                            )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Selamat Datang!",
                            fontSize = 20.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Q: Apa kegunaan aplikasi ini?",
                            fontSize = 16.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aplikasi ini digunakan untuk mendeteksi penyakit pada tanaman selada dengan cepat dan akurat. Hal ini dapat membantu petani dalam mengidentifikasi masalah dan mengambil tindakan yang diperlukan untuk menyelamatkan tanaman.",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Q: Bagaimana cara kerjanya?",
                            fontSize = 16.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Pastikan foto tanaman selada diambil dengan pencahayaan yang baik, tanpa bayangan yang mengganggu, dan fokus pada area daun yang terkena masalah. Hindari foto yang buram atau terlalu jauh dari objek.",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Q: Apa saja yang dapat didiagnosa?",
                            fontSize = 16.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "A: Aplikasi ini dapat mendiagnosa:",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "- Bacterial",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "- Fungal",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "- Shepherd purse weeds",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "- Healthy Lettuce",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Q: Foto bagaimna yang bagus?",
                            fontSize = 16.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Pastikan foto tanaman selada diambil dengan pencahayaan yang baik, tanpa bayangan yang mengganggu, dan fokus pada area daun yang terkena masalah. Hindari foto yang buram atau terlalu jauh dari objek.",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Q: Bagaimana jika tidak memberikan hasil?",
                            fontSize = 16.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Jika aplikasi tidak memberikan hasil, pastikan gambar yang diunggah memiliki kualitas yang baik dan sesuai dengan format yang didukung. Jika masalah tetap terjadi, hubungi tim dukungan teknis untuk bantuan lebih lanjut.",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )


                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Q: Bisakah offline?",
                            fontSize = 16.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aplikasi ini dapat digunakan tanpa koneksi internet karena menggunakan TensorFlow Lite. Model kecerdasan buatan telah diintegrasikan langsung ke dalam aplikasi untuk menganalisis gambar secara lokal.",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )


                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Q: Seberapa akurat aplikasi ini?",
                            fontSize = 16.sp,
                            fontWeight = Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aplikasi ini memiliki tingkat akurasi yang tinggi dalam mendeteksi penyakit, dengan model kecerdasan buatan yang telah dilatih menggunakan ribuan data gambar. Namun, hasil diagnosis tetap memerlukan verifikasi manual oleh pengguna.",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                    }
                }

            }
        }
    }
}