package com.awl1sh.healthsteptrackerpro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.awl1sh.healthsteptrackerpro.ui.viewmodels.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HealthViewModel,
    navController: NavController,
    onOpenHistory: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenHydration: () -> Unit,
    onOpenGoals: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val bmi = if (state.profile.heightCm > 0) {
        state.profile.weightKg / ((state.profile.heightCm / 100.0) * (state.profile.heightCm / 100.0))
    } else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Health dashboard") },
            actions = {
                IconButton(onClick = onOpenSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(20.dp)
        ) {
            Column {
                Text("Today", color = Color.White.copy(alpha = 0.8f))
                Text("Step goal", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${state.steps}/${state.profile.goalSteps}",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(250.dp)
        ) {
            item { MetricCard(label = "Calories", value = "${state.caloriesBurned.toInt()} kcal") }
            item { MetricCard(label = "Distance", value = String.format("%.2f km", state.distanceKm)) }
            item { MetricCard(label = "Active", value = "${state.activeMinutes} min") }
            item { MetricCard(label = "Protein", value = "${state.proteinConsumed} g") }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onOpenHistory, modifier = Modifier.weight(1f)) { Text("History") }
            Button(onClick = onOpenNutrition, modifier = Modifier.weight(1f)) { Text("Nutrition") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onOpenHydration, modifier = Modifier.weight(1f)) { Text("Hydration") }
            Button(onClick = onOpenGoals, modifier = Modifier.weight(1f)) { Text("Goals") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Body metrics", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Weight: ${state.profile.weightKg} kg")
                Text("Height: ${state.profile.heightCm} cm")
                Text("BMI: ${String.format("%.1f", bmi)}")
                Text("Hydration: ${state.hydrationLiters} L / ${state.profile.hydrationGoalLiters} L")
                Text("Progress: ${String.format("%.0f%%", viewModel.hydrationProgress() * 100)}")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onOpenProfile, modifier = Modifier.fillMaxWidth()) {
            Text("Edit profile & body metrics")
        }
    }
}

@Composable
private fun MetricCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = label, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
