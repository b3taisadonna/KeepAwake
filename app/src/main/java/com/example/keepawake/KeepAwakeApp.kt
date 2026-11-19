package com.example.keepawake

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class KeepAwakeApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Create notification channel for foreground service
        createNotificationChannel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.channel_description)
                setShowBadge(false)
                lockscreenVisibility = NotificationManager.VISIBILITY_PUBLIC
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    companion object {
        const val CHANNEL_ID = "keep_awake_channel"
        const val NOTIFICATION_ID = 1
        const val ACTION_TOGGLE = "com.example.keepawake.ACTION_TOGGLE"
    }
}
