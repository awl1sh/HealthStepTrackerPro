package com.awl1sh.healthsteptrackerpro.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StepTrackerManager(context: Context) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    private val stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    private var initialCounter: Float? = null
    private var currentSteps = 0

    private val _stepsFlow = MutableStateFlow(0)
    val stepsFlow = _stepsFlow.asStateFlow()

    fun start() {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI)
        }
        if (stepDetectorSensor != null) {
            sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    fun getCurrentSteps(): Int = currentSteps

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        when (event.sensor.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                val totalSinceBoot = event.values[0]
                if (initialCounter == null) {
                    initialCounter = totalSinceBoot
                }
                currentSteps = (totalSinceBoot - (initialCounter ?: totalSinceBoot)).toInt()
                _stepsFlow.value = currentSteps
            }
            Sensor.TYPE_STEP_DETECTOR -> {
                currentSteps += 1
                _stepsFlow.value = currentSteps
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}

object HealthCalculator {
    fun calculateCaloriesBurned(steps: Int, weightKg: Double): Double {
        if (steps <= 0 || weightKg <= 0) return 0.0
        return (steps * 0.04 * weightKg / 60.0)
    }

    fun calculateDistanceKm(steps: Int): Double {
        return steps * 0.00076
    }

    fun calculateActiveMinutes(steps: Int): Int {
        return (steps / 100).coerceAtLeast(0)
    }

    fun calculateBmi(weightKg: Double, heightCm: Int): Double {
        if (heightCm <= 0) return 0.0
        val heightM = heightCm / 100.0
        return weightKg / (heightM * heightM)
    }
}
