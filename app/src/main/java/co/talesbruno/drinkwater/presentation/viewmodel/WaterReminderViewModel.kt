package co.talesbruno.drinkwater.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.talesbruno.drinkwater.domain.repository.WaterReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WaterReminderViewModel @Inject constructor(private val waterReminderScheduler: WaterReminderScheduler) :
    ViewModel() {
    private val _isReminderScheduled = MutableStateFlow(false)
    val isReminderScheduled: StateFlow<Boolean> = _isReminderScheduled

    private val _startTime = MutableStateFlow(0L)
    val startTime: StateFlow<Long> = _startTime

    init {
        checkReminderStatus()
    }

    fun scheduleReminder(interval: Long) {
        waterReminderScheduler.scheduleReminder(interval)
        startTimer()
    }

    fun cancelReminder() {
        waterReminderScheduler.cancelReminder()
    }

    private fun checkReminderStatus() {
        _isReminderScheduled.value = waterReminderScheduler.isReminderScheduled()
    }

    private fun startTimer() {
        _startTime.value = System.currentTimeMillis()
    }
}

