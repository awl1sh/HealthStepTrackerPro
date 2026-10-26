package com.awl1sh.healthsteptrackerpro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awl1sh.healthsteptrackerpro.ui.viewmodels.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    viewModel: HealthViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsState().value
    val stepProgress = viewModel.stepsProgress()
    val proteinProgress = viewModel.proteinProgress()
    val hydrationProgress = viewModel.hydrationProgress()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Daily goals") },
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
            GoalCard(
                title = "Steps",
                value = "${state.steps} / ${state.profile.goalSteps}",
                progress = stepProgress
            )
            GoalCard(
                title = "Protein",
                value = "${state.proteinConsumed} / ${state.profile.proteinGoalGrams} g",
                progress = proteinProgress
            )
            GoalCard(
                title = "Hydration",
                value = "${String.format("%.2f", state.hydrationLiters)} / ${state.profile.hydrationGoalLiters} L",
                progress = hydrationProgress
            )
        }
    }
}

@Composable
private fun GoalCard(title: String, value: String, progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(value)
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = 8.dp))
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
        }
    }
}
