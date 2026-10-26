package com.awl1sh.healthsteptrackerpro.data.model

import java.time.LocalDate

data class UserProfile(
    val age: Int = 28,
    val gender: String = "Male",
    val heightCm: Int = 175,
    val weightKg: Double = 72.0,
    val goalSteps: Int = 10000,
    val proteinGoalGrams: Int = 150,
    val caloriesGoal: Int = 2200,
    val hydrationGoalLiters: Double = 2.5
)

data class NutritionEntry(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val calories: Int,
    val proteinGrams: Int,
    val date: LocalDate = LocalDate.now()
)

data class StepRecord(
    val date: LocalDate,
    val steps: Int,
    val caloriesBurned: Double,
    val distanceKm: Double
)
