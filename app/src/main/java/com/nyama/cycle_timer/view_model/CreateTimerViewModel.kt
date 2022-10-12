package com.nyama.cycle_timer.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateTimerViewModel : ViewModel() {
    var activityMinutes by mutableStateOf("20")
    var activitySeconds by mutableStateOf("00")
    var breakMinutes by mutableStateOf("05")
    var breakSeconds by mutableStateOf("00")
    var setNumber by mutableStateOf("5")
    var isEndless by mutableStateOf(true)
}