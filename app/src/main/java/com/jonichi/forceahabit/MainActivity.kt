package com.jonichi.forceahabit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.jonichi.notification.NotificationHelper
import com.jonichi.uicommon.theme.ForceAHabitTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var notificationHelper: NotificationHelper

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Log.w("NotificationRequest", if (isGranted) "Granted" else "Not Granted")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationHelper.requestNotificationPermission(requestNotificationPermission)
        notificationHelper.requestExactAlarmPermission(this)
        notificationHelper.createNotificationChannel()
        enableEdgeToEdge()
        setContent {
            ForceAHabitTheme {
                ForceAHabitApp()
            }
        }
    }
}
