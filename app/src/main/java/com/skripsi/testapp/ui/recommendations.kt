package com.skripsi.testapp.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.skripsi.testapp.Disease
import com.skripsi.testapp.DiseaseViewModel
import com.skripsi.testapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RecommendationsScreen(diseaseId: String?, navController: NavHostController) {
    val diseaseViewModel: DiseaseViewModel = viewModel()
    var disease by remember { mutableStateOf<Disease?>(null) }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    BackHandler {
        navController.navigate("home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    if (diseaseId != null) {
        val objectId = ObjectId(diseaseId)
        LaunchedEffect(objectId) {
            withContext(Dispatchers.IO) {
                val result = diseaseViewModel.getDiseaseById(objectId)
                withContext(Dispatchers.Main) {
                    disease = result
                }
            }
        }
    }

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
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Kembali",
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

                        disease?.let {
                            val formattedDate = dateFormat.format(it.dateTaken)
                            Text(
                                text = formattedDate,
                                color = Color.Black,
                                fontWeight = Bold,
                                fontSize = 24.sp,
                            )
                        }
                    }
                }
            }

            item {
                disease?.let {
                    Text(
                        "Hasil Diagnosa",
                        fontSize = 20.sp,
                        fontWeight = Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .aspectRatio(1f)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = Uri.parse(it.imageUri),
                                    onError = { error ->
                                        Log.e(
                                            "ImageLoading",
                                            "Error loading image: ${error.result.throwable}"
                                        )
                                    },
                                    placeholder = painterResource(R.drawable.baseline_image_24)
                                ),
                                contentDescription = "Disease Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column {
                            Text(
                                text = it.treeDisease,
                                fontSize = 16.sp,
                                fontWeight = Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(16.dp)
                            )

                            Text(
                                text = "Detail penyakit",
                                fontSize = 16.sp,
                                color = Color(0xFF585858),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = Color.LightGray, thickness = 1.dp)

                    Text(
                        "Rekomendasi",
                        fontSize = 20.sp,
                        fontWeight = Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(16.dp)
                    )

                    when(it.treeDisease) {
                        "Bacterial" -> { Bacterial() }
                        "Fungal" -> { Fungal() }
                        "Shepherd purse weeds" -> { Shepherd() }
                    }

                    Text(
                        "Apakah informasi ini berguna?",
                        fontSize = 20.sp,
                        fontWeight = Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("feedback")
                            },
                            modifier = Modifier
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF61AF2B),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text("Report Feedback")
                        }
                    }

                } ?: Text("Loading...")
            }
        }
    }
}

@Composable
fun Bacterial(){
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            "Pengendalian dengan Bahan Kimia",
            fontSize = 18.sp,
            fontWeight = Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(id = R.string.bahan_kimia_bacterial),
            fontSize = 16.sp,
            color = Color(0xFF585858),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Praktik budidaya",
            fontSize = 18.sp,
            fontWeight = Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(id = R.string.bahan_tradisional_bacterial),
            fontSize = 16.sp,
            color = Color(0xFF585858)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Composable
fun Fungal() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            "Pengendalian dengan Bahan Kimia",
            fontSize = 18.sp,
            fontWeight = Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(id = R.string.bahan_kimia_fungal),
            fontSize = 16.sp,
            color = Color(0xFF585858),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Praktik budidaya",
            fontSize = 18.sp,
            fontWeight = Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(id = R.string.bahan_tradisional_fungal),
            fontSize = 16.sp,
            color = Color(0xFF585858)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Composable
fun Shepherd() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            "Pengendalian dengan Bahan Kimia",
            fontSize = 18.sp,
            fontWeight = Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(id = R.string.bahan_kimia_shepherd),
            fontSize = 16.sp,
            color = Color(0xFF585858),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Praktik budidaya",
            fontSize = 18.sp,
            fontWeight = Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(id = R.string.bahan_tradisional_shepherd),
            fontSize = 16.sp,
            color = Color(0xFF585858)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}
@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun RecommendationsScreenPreview() {
    HomeScreen(navController = rememberNavController())
}