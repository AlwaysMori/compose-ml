

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.skripsi.testapp.DiseaseViewModel
import com.skripsi.testapp.classifyImage
import com.skripsi.testapp.ui.ImagePicker
import com.skripsi.testapp.R
import com.skripsi.testapp.ui.ScanButton
import com.skripsi.testapp.ui.createBitmapFromUri
import org.bson.types.ObjectId
import kotlin.toString


@Composable
fun ResultScreen(predictionResult: FloatArray?, imageUri: Uri?, navController: NavHostController) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var isFullScreen by remember { mutableStateOf(false) }
    val isSaved = remember { mutableStateOf(false) }
    val savedDiseaseId = remember { mutableStateOf<ObjectId?>(null) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val halfScreenHeight = (screenHeight / 2) + 25.dp
    val targetHeight by animateDpAsState(
        targetValue = if (isFullScreen) screenHeight else halfScreenHeight,
        animationSpec = tween(durationMillis = 1000)
    )
    val diseaseViewModel: DiseaseViewModel = viewModel()
    val noTreeDialog = remember { mutableStateOf(false) }

    BackHandler {
        navController.navigate("home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    if (showDialog.value) {
        ImagePicker(onImageCaptured = { uri ->
            uri?.let {
                bitmap.value = createBitmapFromUri(context, it)
                bitmap.value?.let { bmp ->
                    val classificationResults = classifyImage(context, bmp)
                    navController.navigate(
                        "result/${Uri.encode(it.toString())}/${classificationResults.joinToString(",")}"
                    )
                }
            }
            showDialog.value = false
        }, onDismiss = { showDialog.value = false })
    }

    if (noTreeDialog.value) {
        AlertDialog(
            onDismissRequest = { noTreeDialog.value = false; navController.navigate("home") },
            title = {
                Text(
                    "Tidak ada selada yang terdeteksi!",
                    color = Color.Black,
                    fontWeight = Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    "Tidak ada daun yang terdeteksi!",
                    color = Color(0xFF585858),
                    fontSize = 14.sp
                )
            },
            confirmButton = {},
            dismissButton = {
                Column(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Back to home",
                        modifier = Modifier
                            .clickable {
                                noTreeDialog.value = false
                                navController.navigate("home")
                            }
                            .padding(8.dp),
                        color = Color(0xFF61AF2B)
                    )
                    Text(
                        text = "Scan lagi",
                        modifier = Modifier
                            .clickable { showDialog.value = true }
                            .padding(8.dp),
                        color = Color(0xFF61AF2B)
                    )
                }
            },
            containerColor = Color.White
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "Captured Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(30.dp)
                    .clickable { showDialog.value = true }
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                contentDescription = "Back to Home",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(30.dp)
                    .clickable { navController.navigate("home") }
            )
        }

        var disease by remember { mutableStateOf("No data available") }
        var accuracy by remember { mutableStateOf(0f) }

        LaunchedEffect(predictionResult) {
            predictionResult?.let {
                val result = identifyDisease(it)
                disease = result.first
                accuracy = result.second // Accuracy percentage
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(targetHeight)
                .let { if (!isFullScreen) it.clip(RoundedCornerShape(20.dp)) else it }
                .background(Color.White)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .width(80.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Gray)
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                change.consume()
                                isFullScreen = !isFullScreen
                            }
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.align(Alignment.Start)) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                        contentDescription = "Check",
                        Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Penyakit terdeteksi!",
                        fontSize = 14.sp,
                        color = Color(0xFF61AF2B)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = if (isSaved.value) R.drawable.baseline_check_circle_24 else R.drawable.baseline_bookmark_border_24),
                        contentDescription = "Save",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(enabled = disease != "Healthy" && !isSaved.value) {
                                isSaved.value = true
                                diseaseViewModel.addDisease(context, disease, imageUri.toString()) { diseaseId ->
                                    savedDiseaseId.value = diseaseId
                                }
                            }
                    )
                }

                when (disease) {
                    "Healthy" -> {
                        Text(
                            text = "Tanaman kamu sehat!",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Akurasi: ${"%.2f".format(accuracy)}%",
                            fontSize = 14.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Tanaman kamu dalam kondisi prima dan tidak menunjukkan gejala penyakit.",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    "Bacterial" -> {
                        Bacterial(isFullScreen, accuracy)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    "Fungal" -> {
                        Fungal(isFullScreen, accuracy)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    "Shepherd purse weeds" -> {
                        Shepherd(isFullScreen, accuracy)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    else -> {
                        Text(
                            text = "No data available",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (isSaved.value) {
                            savedDiseaseId.value?.let {
                                navController.navigate("Recommendations/${it.toString()}")
                            }
                        } else {
                            isSaved.value = true
                            diseaseViewModel.addDisease(context, disease, imageUri.toString()) { diseaseId ->
                                savedDiseaseId.value = diseaseId
                                diseaseId?.let {
                                    navController.navigate("Recommendations/${it.toString()}")
                                }
                            }
                        }
                    },
                    enabled = disease != "Healthy",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7FFF4E),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(text = "Detail Penyakit",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                ScanButton(onImageCaptured = {}, navController = navController)
            }
        }
    }
}


fun identifyDisease(predictionResult: FloatArray): Pair<String, Float> {
    val keys = listOf("Bacterial", "Fungal", "Healthy", "Shepherd purse weeds")

    if (predictionResult.isEmpty()) {
        return "No data available" to 0f
    }

    var max = 0
    for (i in predictionResult.indices) {
        if (predictionResult[i] > predictionResult[max]) {
            max = i
        }
    }

    val confidence = predictionResult[max] * 100
    if (confidence > 60) {
        return keys[max] to confidence
    }

    return "No Tree" to 0f
}


@Composable
fun Bacterial(isFullScreen: Boolean, accuracy: Float){
    val fullText = stringResource(id = R.string.bacterial_fulltext)
    val halfText = stringResource(id = R.string.bacterial_halftext)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ){
        Text(
            text = "Bacterial",
            fontSize = 20.sp,
            fontWeight = Bold,
            color = Color.Black
        )

        Text(
            text = "(Bacterial)",
            fontSize = 20.sp,
            fontWeight = Bold,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF0F3F6))
                .size(70.dp, 30.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Lactuca sativa",
                fontSize = 8.sp,
                color = Color.Black
            )
        }
        Text(
            text = "Akurasi: ${"%.2f".format(accuracy)}%",
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Deskripsi",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isFullScreen) fullText else halfText,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun Fungal(isFullScreen: Boolean, accuracy: Float){
    val fullText = stringResource(id = R.string.fungal_fulltext)
    val halfText = stringResource(id = R.string.fungal_fulltext)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ){
        Text(
            text = "Fungal",
            fontSize = 20.sp,
            fontWeight = Bold,
            color = Color.Black
        )
        Text(
            text = "(Jamur)",
            fontSize = 20.sp,
            fontWeight = Bold,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF0F3F6))
                .size(70.dp, 22.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Lactuca sativa",
                fontSize = 8.sp,
                color = Color.Black
            )
        }
        Text(
            text = "Akurasi: ${"%.2f".format(accuracy)}%",
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Deskripsi",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isFullScreen) fullText else halfText,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun Shepherd(isFullScreen: Boolean, accuracy: Float){
    val fullText = stringResource(id = R.string.shepherd_fulltext)
    val halfText = stringResource(id = R.string.shepherd_halftext)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ){
        Text(
            text = "Shepherd purse weeds",
            fontSize = 20.sp,
            fontWeight = Bold,
            color = Color.Black
        )
        Text(
            text = "(Capsella bursa-pastoris)",
            fontSize = 20.sp,
            fontWeight = Bold,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF0F3F6))
                .size(70.dp, 22.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Lactuca sativa",
                fontSize = 8.sp,
                color = Color.Black
            )
        }
        Text(
            text = "Akurasi: ${"%.2f".format(accuracy)}%",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Deskripsi",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isFullScreen) fullText else halfText,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

