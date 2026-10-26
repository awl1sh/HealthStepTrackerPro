package com.awl1sh.healthsteptrackerpro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awl1sh.healthsteptrackerpro.data.model.UserProfile
import com.awl1sh.healthsteptrackerpro.ui.viewmodels.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: HealthViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsState().value
    val age = remember { mutableStateOf(state.profile.age.toString()) }
    val weight = remember { mutableStateOf(state.profile.weightKg.toString()) }
    val height = remember { mutableStateOf(state.profile.heightCm.toString()) }
    val steps = remember { mutableStateOf(state.profile.goalSteps.toString()) }
    val protein = remember { mutableStateOf(state.profile.proteinGoalGrams.toString()) }
    val calories = remember { mutableStateOf(state.profile.caloriesGoal.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Profile & body metrics") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = age.value,
                onValueChange = { age.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Age") }
            )
            OutlinedTextField(
                value = weight.value,
                onValueChange = { weight.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Weight (kg)") }
            )
            OutlinedTextField(
                value = height.value,
                onValueChange = { height.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Height (cm)") }
            )
            OutlinedTextField(
                value = steps.value,
                onValueChange = { steps.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Daily goal steps") }
            )
            OutlinedTextField(
                value = protein.value,
                onValueChange = { protein.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Protein goal (g)") }
            )
            OutlinedTextField(
                value = calories.value,
                onValueChange = { calories.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Calories goal") }
            )

            Button(
                onClick = {
                    val profile = UserProfile(
                        age = age.value.toIntOrNull() ?: state.profile.age,
                        gender = state.profile.gender,
                        heightCm = height.value.toIntOrNull() ?: state.profile.heightCm,
                        weightKg = weight.value.toDoubleOrNull() ?: state.profile.weightKg,
                        goalSteps = steps.value.toIntOrNull() ?: state.profile.goalSteps,
                        proteinGoalGrams = protein.value.toIntOrNull() ?: state.profile.proteinGoalGrams,
                        caloriesGoal = calories.value.toIntOrNull() ?: state.profile.caloriesGoal,
                        hydrationGoalLiters = state.profile.hydrationGoalLiters
                    )
                    viewModel.updateProfile(profile)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save body data")
            }
        }
    }
}
