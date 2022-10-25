package com.nyama.cycle_timer

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.nyama.cycle_timer.data.TimerData
import com.nyama.cycle_timer.ui.timer.TimerScreen
import com.nyama.cycle_timer.ui.create_timer.CreateTimerScreen
import com.nyama.cycle_timer.ui.theme.Cycle_timerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(1)
            .build()

        setContent {
            Cycle_timerTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = CreateTimer.route,
                    ) {
                        composable(route = CreateTimer.route) {
                            CreateTimerScreen(
                                onClickStartButton = {timerJson ->
                                    navController.navigate(Timer.route.replace("{timer_data}", timerJson))
                                }
                            )
                        }
                        composable(route = Timer.route) {backStackEntry ->
                            val timerJson = backStackEntry.arguments?.getString("timer_data")
                            val timerDataObject = Gson().fromJson(timerJson, TimerData::class.java)

                            TimerScreen(
                                timerDataObject,
                                soundPool,
                                onClickTopButton = {
                                    navController.navigate(CreateTimer.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Cycle_timerTheme {
        Greeting("Android")
    }
}