package com.nyama.cycle_timer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.nyama.cycle_timer.data.TimerData

@Composable
fun TimerScreen(
    timer_data: TimerData
) {
    Surface() {
        Column() {
            Text(text = timer_data.activity_time)
            Text(text = timer_data.break_time)
            Text(text = timer_data.set_number.toString())
        }
    }
}