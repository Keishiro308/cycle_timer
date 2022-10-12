package com.nyama.cycle_timer.utils

import java.util.concurrent.TimeUnit

object Utility {
    fun Long.formatTime(): String = String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )
}