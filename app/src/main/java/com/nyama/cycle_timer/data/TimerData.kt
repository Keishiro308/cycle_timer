package com.nyama.cycle_timer.data

data class TimerData(
    val activityMinutes: Int,
    val activitySeconds: Int,
    val breakMinutes: Int,
    val breakSeconds: Int,
    val setNumber: Int,
    val isEndless: Boolean
    ) {}