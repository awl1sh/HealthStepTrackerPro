package com.awl1sh.healthsteptrackerpro.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awl1sh.healthsteptrackerpro.data.model.NutritionEntry
import com.awl1sh.healthsteptrackerpro.data.model.StepRecord
import com.awl1sh.healthsteptrackerpro.data.model.UserProfile
import com.awl1sh.healthsteptrackerpro.data.repository.HealthRepository
import com.awl1sh.healthsteptrackerpro.utils.HealthCalculator
import com.awl1sh.healthsteptrackerpro.utils.StepTrackerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class HealthUiState(
    val steps: Int = 0,
    val caloriesBurned: Double = 0.0,
    val distanceKm: Double = 0.0,
    val activeMinutes: Int = 0,
    val proteinConsumed: Int = 0,
    val caloriesConsumed: Int = 0,
    val hydrationLiters: Double = 0.0,
    val profile: UserProfile = UserProfile(),
    val nutritionEntries: List<NutritionEntry> = emptyList(),
    val stepHistory: List<StepRecord> = emptyList()
)

class HealthViewModel(
    private val repository: HealthRepository,
    private val stepTracker: StepTrackerManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HealthUiState())
    val uiState: StateFlow<HealthUiState> = _uiState.asStateFlow()

    init {
        stepTracker.stepsFlow.onEach { steps ->
            val profile = _uiState.value.profile
            val calories = HealthCalculator.calculateCaloriesBurned(steps, profile.weightKg)
            val distance = HealthCalculator.calculateDistanceKm(steps)
            val minutes = HealthCalculator.calculateActiveMinutes(steps)

            _uiState.update {
                it.copy(
                    steps = steps,
                    caloriesBurned = calories,
                    distanceKm = distance,
                    activeMinutes = minutes,
                    proteinConsumed = it.proteinConsumed,
                    caloriesConsumed = it.caloriesConsumed,
                    hydrationLiters = it.hydrationLiters,
                    profile = it.profile,
                    nutritionEntries = it.nutritionEntries,
                    stepHistory = it.stepHistory
                )
            }
        }

        repository.profileFlow.onEach { profile ->
            _uiState.update { current ->
                current.copy(profile = profile)
            }
        }
    }

    fun updateProfile(profile: UserProfile) {
        repository.saveProfile(profile)
        _uiState.update { it.copy(profile = profile) }
    }

    fun addNutrition(entry: NutritionEntry) {
        val updated = _uiState.value.nutritionEntries + entry
        val protein = updated.sumOf { it.proteinGrams }
        val calories = updated.sumOf { it.calories }

        _uiState.update {
            it.copy(
                nutritionEntries = updated,
                proteinConsumed = protein,
                caloriesConsumed = calories
            )
        }
    }

    fun addHydration(amountLiters: Double) {
        _uiState.update { it.copy(hydrationLiters = it.hydrationLiters + amountLiters) }
    }

    fun logDailyStepSnapshot() {
        val current = _uiState.value
        val record = StepRecord(
            date = LocalDate.now(),
            steps = current.steps,
            caloriesBurned = current.caloriesBurned,
            distanceKm = current.distanceKm
        )
        val updated = listOf(record) + current.stepHistory
        _uiState.update { it.copy(stepHistory = updated.take(7)) }
    }

    fun refreshFromSensor() {
        _uiState.update {
            val steps = stepTracker.getCurrentSteps()
            it.copy(
                steps = steps,
                caloriesBurned = HealthCalculator.calculateCaloriesBurned(steps, it.profile.weightKg),
                distanceKm = HealthCalculator.calculateDistanceKm(steps),
                activeMinutes = HealthCalculator.calculateActiveMinutes(steps)
            )
        }
    }
}

class HealthViewModelFactory(
    private val repository: HealthRepository,
    private val stepTracker: StepTrackerManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HealthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HealthViewModel(repository, stepTracker) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
