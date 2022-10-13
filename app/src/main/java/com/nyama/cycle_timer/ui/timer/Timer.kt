package com.nyama.cycle_timer.ui.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nyama.cycle_timer.data.TimerData
import com.nyama.cycle_timer.utils.Utility.formatTime
import com.nyama.cycle_timer.view_model.CreateTimerViewModel
import com.nyama.cycle_timer.view_model.TimerViewModel

@Composable
fun TimerScreen(
    timerData: TimerData,
    viewModel: TimerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onClickTopButton: () -> Unit
) {
    viewModel.setter(timerData)
    val activityMinutes = viewModel.activityMinutes.observeAsState()
    val activitySeconds = viewModel.activitySeconds.observeAsState()
    val breakMinutes = viewModel.breakMinutes.observeAsState()
    val breakSeconds = viewModel.breakSeconds.observeAsState()
    val currentSet = viewModel.currentSet.observeAsState()
    val time = viewModel.time.observeAsState()
    val isPlaying = viewModel.isPlaying.observeAsState()
    val isCompleted = viewModel.isCompleted.observeAsState()
    val isActivity = viewModel.isActivity.observeAsState()
    val isLastSet = viewModel.isLastSet.observeAsState()
    val activityTime = viewModel.getMilliseconds(activityMinutes.value!!, activitySeconds.value!!).formatTime()
    val breakTime = viewModel.getMilliseconds(breakMinutes.value!!, breakSeconds.value!!).formatTime()

    Surface() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isCompleted.value!!) {
                Text(
                    text = "Completed!",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(bottom = 120.dp)
                )
                Text(
                    text = "${currentSet.value!!}セット完了しました!",
                    style = TextStyle.Default.copy(fontSize = 30.sp),
                    modifier = Modifier.padding(bottom = 80.dp)
                )
                Text(
                    text = "1セット当たりの活動時間：${activityTime}",
                    style = TextStyle.Default.copy(fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    text = "1セット当たりの休憩時間：${breakTime}",
                    style = TextStyle.Default.copy(fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                OutlinedButton(onClick = onClickTopButton) {
                    Text(text = "Top", modifier = Modifier.padding(16.dp, 8.dp))
                }
            } else {

                var text: String?
                if (isActivity.value!!) {
                    text = "Activity"
                } else {
                    text = "Break"
                }
                Row() {
                    Text(
                        text = "${currentSet.value!!}セット目",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(bottom = 30.dp, end = 15.dp)
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                }
                Text(
                    text = time.value!!,
                    style = TextStyle.Default.copy(fontSize = 48.sp),
                    modifier = Modifier.padding(bottom = 30.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {viewModel.handleCountDownTimer()},
                    ) {
                        if (isPlaying.value!!) {
                            Text(text = "Finish", modifier = Modifier.padding(16.dp, 8.dp))
                        } else {
                            Text(text = "Start", modifier = Modifier.padding(16.dp, 8.dp))
                        }
                    }
                    if (!isLastSet.value!!) {
                        OutlinedButton(
                            onClick = {viewModel.goNext()},
                            enabled = isPlaying.value!!
                        ) {
                            if (isActivity.value!!) {
                                Text(text = "Next Break", modifier = Modifier.padding(16.dp, 8.dp))
                            } else {
                                Text(text = "Next Activity", modifier = Modifier.padding(16.dp, 8.dp))
                            }
                        }
                    }
                }
            }
            
        }
    }
}