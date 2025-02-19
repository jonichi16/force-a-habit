package com.jonichi.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint(BroadcastReceiver::class)
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        Log.d("NotificationReceiver", "Alarm received! Showing notification...")

        val notificationHelper =
            EntryPointAccessors.fromApplication(
                context,
                NotificationHelperEntryPoint::class.java,
            ).getNotificationHelper()

        notificationHelper.showNotification()
    }
}
