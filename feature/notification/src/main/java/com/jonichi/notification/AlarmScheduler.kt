package com.jonichi.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmScheduler
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        companion object {
            private const val REQUEST_CODE_FACTOR = 100
        }

        fun scheduleNotification(
            hour: Int,
            minute: Int,
        ) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent =
                Intent(context, NotificationReceiver::class.java).apply {
                    setPackage(context.packageName) // Ensure it's sent within the app
                }
            val requestCode = (hour * REQUEST_CODE_FACTOR) + minute
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE,
                )

            val calendar =
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    if (timeInMillis < System.currentTimeMillis()) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent,
            )
            Log.d("AlarmScheduler", "Alarm set for: ${calendar.time}")
        }
    }
