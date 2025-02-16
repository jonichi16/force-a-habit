package com.jonichi.notification

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NotificationHelperEntryPoint {
    fun getNotificationHelper(): NotificationHelper
}
