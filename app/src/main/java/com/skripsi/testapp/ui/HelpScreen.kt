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
                        .background(Color(0xFFD0FFCF))
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
                            text = "Lorem",
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
                            text = "Lorem",
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
                            text = "Lorem",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "Lorem",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "Lorem",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "Lorem",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "Lorem",
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
                            text = "Lorem",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "Lorem",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                        Text(
                            text = "Lorem",
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
                            text = "Lorem",
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
                            text = "Lorem",
                            fontSize = 16.sp,
                            color = Color(0xFF585858)
                        )
                    }
                }

            }
        }
    }
}