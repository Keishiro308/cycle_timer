package com.nyama.cycle_timer.view_model

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nyama.cycle_timer.data.TimerData
import com.nyama.cycle_timer.utils.Utility.formatTime

class TimerViewModel: ViewModel() {
    private val _activityMinutes = MutableLiveData(20)
    private val _activitySeconds = MutableLiveData(0)
    private val _breakMinutes = MutableLiveData(5)
    private val _breakSeconds = MutableLiveData(0)
    private val _setNumber = MutableLiveData(5)
    private val _isActivity = MutableLiveData(true)
    private  val _time = MutableLiveData("20:00")
    private val _isPlaying = MutableLiveData(false)
    private val _isCompleted = MutableLiveData(false)
    private val _isLastSet = MutableLiveData(false)
    private val _isEndless = MutableLiveData(false)
    private val _currentSet = MutableLiveData(1)

    private var countDownTimer: CountDownTimer? = null

    val activityMinutes: LiveData<Int> = _activityMinutes
    val activitySeconds: LiveData<Int> = _activitySeconds
    val breakMinutes: LiveData<Int> = _breakMinutes
    val breakSeconds: LiveData<Int> = _breakSeconds
    val setNumber: LiveData<Int> = _setNumber
    val currentSet: LiveData<Int> = _currentSet
    val isActivity: LiveData<Boolean> = _isActivity
    val isCompleted: LiveData<Boolean> = _isCompleted
    val isLastSet: LiveData<Boolean> = _isLastSet
    val isEndless: LiveData<Boolean> = _isEndless
    val time: LiveData<String> = _time
    val isPlaying: LiveData<Boolean> = _isPlaying

    fun setter(timerData: TimerData) {
        _activityMinutes.value = timerData.activityMinutes
        _activitySeconds.value = timerData.activitySeconds
        _breakMinutes.value = timerData.breakMinutes
        _breakSeconds.value = timerData.breakSeconds
        _setNumber.value = timerData.setNumber
        _time.value = getMilliseconds(_activityMinutes.value!!, _activitySeconds.value!!).formatTime()
        _isEndless.value = timerData.isEndless
    }

    fun handleCountDownTimer() {
        if (isPlaying.value == true) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    fun goNext() {
        var milliseconds: Long
        if (isPlaying.value == true) {
            milliseconds = getMilliseconds(_breakMinutes.value!!, _breakSeconds.value!!)
            handleTimerValues(false, milliseconds.formatTime())
        } else {
            _currentSet.value = _currentSet.value!! + 1
            if (_currentSet.value == _setNumber.value) {
                _isLastSet.value = true
            }
            milliseconds = getMilliseconds(_activityMinutes.value!!, _activitySeconds.value!!)
            handleTimerValues(true, milliseconds.formatTime())
        }
        startTimer()
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        _isCompleted.value = true
    }

    private fun startTimer() {

        var milliseconds: Long
        _isPlaying.value = true

        if (_isActivity.value!!) {
            milliseconds = getMilliseconds(_activityMinutes.value!!, _activitySeconds.value!!)
        } else {
            milliseconds = getMilliseconds(_breakMinutes.value!!, _breakSeconds.value!!)
        }
        countDownTimer = object : CountDownTimer(milliseconds, 1000) {

            override fun onTick(millisRemaining: Long) {
                handleTimerValues(true, millisRemaining.formatTime())
            }

            override fun onFinish() {
                if(_isActivity.value!!) {
                    _isActivity.value = false
                    _time.value = getMilliseconds(_breakMinutes.value!!, _breakSeconds.value!!).formatTime()
                    startTimer()
                } else {
                    if (!_isEndless.value!! && _currentSet.value!! >= _setNumber.value!!) {
                        pauseTimer()
                    } else {
                        _isActivity.value = true
                        _currentSet.value = _currentSet.value!! + 1
                        if (_currentSet.value == _setNumber.value) {
                            _isLastSet.value = true
                        }
                        _time.value = getMilliseconds(_activityMinutes.value!!, _activitySeconds.value!!).formatTime()
                        startTimer()
                    }
                }
            }
        }.start()
    }

    fun getMilliseconds(minutes: Int, seconds: Int) : Long {
        return (minutes * 60 * 1000 + seconds * 1000).toLong()
    }

    private fun handleTimerValues(isPlaying: Boolean, text: String) {
        _time.value = text
        _isPlaying.value = isPlaying
    }
}