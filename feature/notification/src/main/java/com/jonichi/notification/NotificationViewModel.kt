package com.jonichi.notification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel
    @Inject
    constructor(
        private val alarmScheduler: AlarmScheduler,
    ) : ViewModel() {
        fun scheduleHabitReminder(
            hour: Int,
            minute: Int,
        ) {
            alarmScheduler.scheduleNotification(hour, minute)
        }
    }
