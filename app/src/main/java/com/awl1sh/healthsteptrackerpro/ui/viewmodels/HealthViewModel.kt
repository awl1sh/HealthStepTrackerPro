package com.awl1sh.healthsteptrackerpro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.awl1sh.healthsteptrackerpro.data.repository.HealthRepository
import com.awl1sh.healthsteptrackerpro.ui.screens.HomeScreen
import com.awl1sh.healthsteptrackerpro.ui.screens.NutritionScreen
import com.awl1sh.healthsteptrackerpro.ui.screens.ProfileScreen
import com.awl1sh.healthsteptrackerpro.ui.screens.SettingsScreen
import com.awl1sh.healthsteptrackerpro.ui.screens.SplashScreen
import com.awl1sh.healthsteptrackerpro.ui.screens.StepHistoryScreen
import com.awl1sh.healthsteptrackerpro.ui.viewmodels.HealthViewModel
import com.awl1sh.healthsteptrackerpro.ui.viewmodels.HealthViewModelFactory
import com.awl1sh.healthsteptrackerpro.utils.StepTrackerManager

@Composable
fun HealthApp(tracker: StepTrackerManager) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val repository = HealthRepository(context.applicationContext)
    val viewModel: HealthViewModel = viewModel(
        factory = HealthViewModelFactory(repository = repository, stepTracker = tracker)
    )

    val uiState by viewModel.uiState.collectAsState()
    if (uiState.profile.goalSteps == 0) {
        // ignore, profile defaults loaded from DataStore
    }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                navController = navController,
                onOpenHistory = { navController.navigate("history") },
                onOpenNutrition = { navController.navigate("nutrition") },
                onOpenProfile = { navController.navigate("profile") },
                onOpenSettings = { navController.navigate("settings") }
            )
        }

        composable("history") {
            StepHistoryScreen(viewModel = viewModel, navController = navController)
        }

        composable("nutrition") {
            NutritionScreen(viewModel = viewModel, navController = navController)
        }

        composable("profile") {
            ProfileScreen(viewModel = viewModel, navController = navController)
        }

        composable("settings") {
            SettingsScreen(viewModel = viewModel, navController = navController)
        }
    }
}
