package com.awl1sh.healthsteptrackerpro.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.awl1sh.healthsteptrackerpro.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "health_step_tracker")

class HealthRepository(private val context: Context) {
    private val ageKey = intPreferencesKey("age")
    private val genderKey = stringPreferencesKey("gender")
    private val heightKey = intPreferencesKey("height_cm")
    private val weightKey = doublePreferencesKey("weight_kg")
    private val targetStepsKey = intPreferencesKey("goal_steps")
    private val proteinGoalKey = intPreferencesKey("protein_goal")
    private val caloriesGoalKey = intPreferencesKey("calories_goal")
    private val hydrationGoalKey = doublePreferencesKey("hydration_goal_liters")

    val profileFlow: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            age = prefs[ageKey] ?: 28,
            gender = prefs[genderKey] ?: "Male",
            heightCm = prefs[heightKey] ?: 175,
            weightKg = prefs[weightKey] ?: 72.0,
            goalSteps = prefs[targetStepsKey] ?: 10000,
            proteinGoalGrams = prefs[proteinGoalKey] ?: 150,
            caloriesGoal = prefs[caloriesGoalKey] ?: 2200,
            hydrationGoalLiters = prefs[hydrationGoalKey] ?: 2.5
        )
    }

    suspend fun saveProfile(profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[ageKey] = profile.age
            prefs[genderKey] = profile.gender
            prefs[heightKey] = profile.heightCm
            prefs[weightKey] = profile.weightKg
            prefs[targetStepsKey] = profile.goalSteps
            prefs[proteinGoalKey] = profile.proteinGoalGrams
            prefs[caloriesGoalKey] = profile.caloriesGoal
            prefs[hydrationGoalKey] = profile.hydrationGoalLiters
        }
    }
}
