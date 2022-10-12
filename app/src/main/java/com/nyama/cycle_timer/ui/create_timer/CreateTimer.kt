package com.nyama.cycle_timer.ui.create_timer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.nyama.cycle_timer.data.TimerData
import com.nyama.cycle_timer.view_model.CreateTimerViewModel
@Composable
fun CreateTimerScreen(
    onClickStartButton: (String) -> Unit = {_ ->},
    viewModel: CreateTimerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        SettingTimer(
            title = "Activity",
            minutes = viewModel.activityMinutes,
            seconds = viewModel.activitySeconds,
            viewModel = viewModel
        )
        SettingTimer(
            title = "Break",
            minutes = viewModel.breakMinutes,
            seconds = viewModel.breakSeconds,
            viewModel = viewModel
        )
        SettingSets(
            value = viewModel.setNumber,
            viewModel = viewModel
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {
                    onClickStartButton(
                        Gson().toJson(
                            TimerData(
                                viewModel.activityMinutes.toInt(),
                                viewModel.activitySeconds.toInt(),
                                viewModel.breakMinutes.toInt(),
                                viewModel.breakSeconds.toInt(),
                                viewModel.setNumber.toInt(),
                                viewModel.isEndless
                            )
                        )
                    )
                },
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
fun SettingTimer(title: String, minutes: String, seconds: String, viewModel: CreateTimerViewModel) {
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
                onValueChange = {
                    if (title == "Activity") {
                        viewModel.activityMinutes = it
                    } else {
                        viewModel.breakMinutes = it
                    }
                },
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
                onValueChange = {
                    if (title == "Activity") {
                        viewModel.activitySeconds = it
                    } else {
                        viewModel.breakSeconds = it
                    }
                },
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
fun SettingSets(value: String, viewModel: CreateTimerViewModel) {
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
                                onClick = {
                                    onOptionSelected(text)
                                    viewModel.isEndless = text == "Endless"
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null,
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    if (text == "Set manually") {
                        OutlinedTextField(
                            value = value,
                            onValueChange = { viewModel.setNumber = it },
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