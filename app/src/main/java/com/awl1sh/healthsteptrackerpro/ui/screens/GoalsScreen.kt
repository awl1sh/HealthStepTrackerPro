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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awl1sh.healthsteptrackerpro.ui.viewmodels.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HydrationScreen(
    viewModel: HealthViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsState().value
    val progress = viewModel.hydrationProgress()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Hydration") },
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Water intake", fontWeight = FontWeight.Bold)
                    Text("${String.format("%.2f", state.hydrationLiters)} / ${state.profile.hydrationGoalLiters} L")
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { viewModel.addHydration(0.25) }, modifier = Modifier.weight(1f)) {
                    Text("+250ml")
                }
                Button(onClick = { viewModel.addHydration(0.5) }, modifier = Modifier.weight(1f)) {
                    Text("+500ml")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { viewModel.addHydration(0.75) }, modifier = Modifier.weight(1f)) {
                    Text("+750ml")
                }
                Button(onClick = { viewModel.addHydration(1.0) }, modifier = Modifier.weight(1f)) {
                    Text("+1L")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Hydration tips", fontWeight = FontWeight.Bold)
                    Text("- Drink water before meals")
                    Text("- Keep a bottle nearby")
                    Text("- Replace sugar drinks with water")
                }
            }
        }
    }
}
