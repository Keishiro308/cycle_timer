package com.nyama.cycle_timer.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateTimerViewModel : ViewModel() {
    var activity_minutes by mutableStateOf("20")
    var activity_seconds by mutableStateOf("00")
    var break_minutes by mutableStateOf("05")
    var break_seconds by mutableStateOf("00")
    var sets_number by mutableStateOf("5")
}