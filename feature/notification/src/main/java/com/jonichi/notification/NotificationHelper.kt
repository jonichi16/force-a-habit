package com.jonichi.notification

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        fun requestNotificationPermission(requestLauncher: ActivityResultLauncher<String>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        fun requestExactAlarmPermission(activity: ComponentActivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val alarmManager = activity.getSystemService(AlarmManager::class.java)
                if (!alarmManager.canScheduleExactAlarms()) {
                    Log.w("AlarmPermission", "Requesting exact alarm permission")
                    val intent = Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    activity.startActivity(intent)
                }
            }
        }

        fun createNotificationChannel() {
            val channel =
                NotificationChannel(
                    "habit_channel",
                    "Habit",
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    description = "Used to notify to do the habit"
                }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            Log.d("Notification Channel", "Notification channel created!")
        }

        fun showNotification() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.w("NotificationHelper", "Notification permission not granted!")
                    return
                }
            }

            val builder =
                NotificationCompat.Builder(context, "habit_channel")
                    .setSmallIcon(R.drawable.baseline_info_24)
                    .setContentTitle("Habit Reminder")
                    .setContentText("It's time to do your habit!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(1, builder.build())
            }
        }
    }
