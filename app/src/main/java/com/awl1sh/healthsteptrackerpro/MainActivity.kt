package com.awl1sh.healthsteptrackerpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.awl1sh.healthsteptrackerpro.ui.theme.HealthTheme
import com.awl1sh.healthsteptrackerpro.utils.StepTrackerManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val tracker = remember { StepTrackerManager(context.applicationContext) }

            DisposableEffect(tracker) {
                tracker.start()
                onDispose { tracker.stop() }
            }

            HealthTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HealthApp(tracker = tracker)
                }
            }
        }
    }
}
