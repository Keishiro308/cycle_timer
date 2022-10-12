package com.nyama.cycle_timer

interface CycleTimerDestination {
    val route: String
}

object CreateTimer : CycleTimerDestination {
    override val route = "create_timer"
}

object Timer : CycleTimerDestination {
    override val route = "timer/timer_data={timer_data}"
}