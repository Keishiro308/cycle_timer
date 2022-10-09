package com.nyama.cycle_timer

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
import com.nyama.cycle_timer.ui.theme.Cycle_timerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cycle_timerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TimerAddScreen("Activity")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun TimerAddScreen(title: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        SettingTimer(title = "Activity", minutes = "20", seconds = "00")
        SettingTimer(title = "Break", minutes = "05", seconds = "00")
        SettingSets()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(16.dp)

            ) {
                Text(
                    text = "Start",
                    modifier = Modifier.padding(16.dp, 8.dp)
                )
            }
        }
    }
}

@Composable
fun SettingTimer(title: String, minutes: String, seconds: String) {
    var minutes by rememberSaveable { mutableStateOf(minutes) }
    var seconds by rememberSaveable { mutableStateOf(seconds) }
    Column() {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = minutes,
                onValueChange = { minutes = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.size(96.dp, 84.dp),
                label = { Text(text = "minutes") },
                textStyle = TextStyle.Default.copy(fontSize = 28.sp)
            )
            Text(
                text = ":",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            OutlinedTextField(
                value = seconds,
                onValueChange = { seconds = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.size(96.dp, 84.dp),
                label = { Text(text = "seconds") },
                textStyle = TextStyle.Default.copy(fontSize = 28.sp)
            )
        }
    }
}

@Composable
fun SettingSets() {
    Column() {
        Text(
            text = "Sets",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        val radioOptions = listOf("Endless", "Set manually")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Column() {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    if (text == "Set manually") {
                        var value by rememberSaveable { mutableStateOf("5") }
                        OutlinedTextField(
                            value = value,
                            onValueChange = { value = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .padding(start = 70.dp)
                                .size(80.dp, 60.dp),
                            label = { Text(text = "sets") },
                            enabled = selectedOption == "Set manually"
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Cycle_timerTheme {
        Greeting("Android")
    }
}