package com.awl1sh.healthsteptrackerpro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awl1sh.healthsteptrackerpro.data.model.NutritionEntry
import com.awl1sh.healthsteptrackerpro.ui.viewmodels.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionScreen(
    viewModel: HealthViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsState().value
    val foodName = remember { mutableStateOf("Chicken breast") }
    val calories = remember { mutableStateOf("300") }
    val protein = remember { mutableStateOf("40") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Nutrition") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nutrition goals", fontWeight = FontWeight.Bold)
                        Text("Protein: ${state.proteinConsumed}/${state.profile.proteinGoalGrams} g")
                        Text("Calories: ${state.caloriesConsumed}/${state.profile.caloriesGoal} kcal")
                        Text("Hydration: ${state.hydrationLiters} / ${state.profile.hydrationGoalLiters} L")
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = foodName.value,
                    onValueChange = { foodName.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Food name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = calories.value,
                        onValueChange = { calories.value = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("kcal") }
                    )
                    OutlinedTextField(
                        value = protein.value,
                        onValueChange = { protein.value = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Protein") }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val c = calories.value.toIntOrNull() ?: 0
                        val p = protein.value.toIntOrNull() ?: 0
                        if (foodName.value.isNotBlank()) {
                            viewModel.addNutrition(
                                NutritionEntry(
                                    name = foodName.value,
                                    calories = c,
                                    proteinGrams = p
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add food")
                }
            }

            items(state.nutritionEntries) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text("${item.proteinGrams} g / ${item.calories} kcal")
                    }
                }
            }
        }
    }
}
