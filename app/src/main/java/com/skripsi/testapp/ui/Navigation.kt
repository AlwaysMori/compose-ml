package com.skripsi.testapp.ui

import ResultScreen
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skripsi.testapp.FeedbackScreen
import com.skripsi.testapp.R
import androidx.compose.ui.graphics.graphicsLayer
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        topBar = {
            if (currentDestination != "result/{imageUri}/{classificationResult}" &&
                currentDestination != "recommendations/{diseaseId}" &&
                currentDestination != "feedback" &&
                currentDestination != "help"
            ) {
                GradientTopAppBar(navController)
            }
        },
        bottomBar = {
            if (currentDestination != "result/{imageUri}/{classificationResult}" &&
                currentDestination != "recommendations/{diseaseId}" &&
                currentDestination != "feedback" &&
                currentDestination != "help"
            ) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            NavigationHost(navController)
        }
    }
}

@Composable
fun GradientTopAppBar(navController: NavController) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF6EC1E4), // Warna awal (biru muda)
                        Color(0xFF56E39F)  // Warna akhir (hijau muda)
                    )
                )

                )
    ) {
        TopAppBar(
            title = {
                Text(
                    "HiCare",
                    color = Color.White, // Warna teks
                    fontWeight = FontWeight.Bold,
                )
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.padding(8.dp)
                )
            },
            actions = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_help_24),
                    contentDescription = "Help",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("help")
                        }
                )
            },
            backgroundColor = Color.Transparent, // Transparan untuk menampilkan gradien
            elevation = 4.dp
        )
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("History") {
            HistoryScreen(navController)
        }

        composable(
            "result/{imageUri}/{classificationResult}",
            arguments = listOf(
                navArgument("imageUri") { type = NavType.StringType },
                navArgument("classificationResult") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")?.let { Uri.parse(it) }
            val classificationResult = backStackEntry.arguments?.getString("classificationResult")
            val floatArray = classificationResult?.split(",")?.map { it.toFloat() }?.toFloatArray()
            ResultScreen(predictionResult = floatArray, imageUri = imageUri, navController)
        }

        composable("recommendations/{diseaseId}") { backStackEntry ->
            val diseaseId = backStackEntry.arguments?.getString("diseaseId")
            RecommendationsScreen(diseaseId = diseaseId, navController)
        }
        composable("feedback") {
            FeedbackScreen(navController)
        }

        composable("help") {
            HelpScreen(navController)
        }

    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("", "home", painterResource(id = R.drawable.baseline_home_24)),
        BottomNavItem("", "History", painterResource(id = R.drawable.baseline_history_24))
    )

    // Observe the current destination of the NavController
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        items.forEach { item ->
            val isSelected = currentDestination == item.route // Check if the item is selected

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (currentDestination != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .width(60.dp)
                            .background(
                                color = if (isSelected) Color(0xFF61F878) else Color.Transparent,
                                shape = RoundedCornerShape(16.dp) // Rounded corners
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = item.icon,
                            contentDescription = item.name,
                            tint = if (isSelected) Color.White else Color.Black
                        )
                    }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Painter
)
