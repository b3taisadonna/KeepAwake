package com.example.keepawake

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat

class KeepAwakeService : Service() {
    
    private var wakeLock: PowerManager.WakeLock? = null
    private var isRunning = false
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize wake lock
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or 
            PowerManager.ACQUIRE_CAUSES_WAKEUP or
            PowerManager.ON_AFTER_RELEASE,
            "KeepAwake::WakeLock"
        )
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == KeepAwakeApp.ACTION_TOGGLE) {
            if (isRunning) {
                stopKeepAwake()
            } else {
                startKeepAwake()
            }
        }
        
        return START_STICKY
    }
    
    private fun startKeepAwake() {
        if (isRunning) return
        
        // Acquire wake lock
        wakeLock?.acquire(10 * 60 * 1000L /* 10 minutes */)
        
        // Start as foreground service
        startForeground()
        
        isRunning = true
        
        // Update widget
        KeepAwakeWidget.updateAllWidgets(this, true)
    }
    
    private fun stopKeepAwake() {
        if (!isRunning) return
        
        // Release wake lock
        wakeLock?.release()
        
        // Stop foreground service
        stopForeground(true)
        stopSelf()
        
        isRunning = false
        
        // Update widget
        KeepAwakeWidget.updateAllWidgets(this, false)
    }
    
    private fun startForeground() {
        val notification = createNotification()
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(KeepAwakeApp.NOTIFICATION_ID, notification)
        } else {
            // For older versions, we need to use startForeground with a different approach
            startForeground(KeepAwakeApp.NOTIFICATION_ID, notification)
        }
    }
    
    private fun createNotification(): Notification {
        val toggleIntent = Intent(this, KeepAwakeService::class.java).apply {
            action = KeepAwakeApp.ACTION_TOGGLE
        }
        
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getService(
                this, 
                0, 
                toggleIntent, 
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getService(
                this, 
                0, 
                toggleIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        
        val openAppIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val openPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this, 
                1, 
                openAppIntent, 
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                this, 
                1, 
                openAppIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        
        return NotificationCompat.Builder(this, KeepAwakeApp.CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text))
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_lock_idle_lock,
                    getString(R.string.stop),
                    pendingIntent
                )
            )
            .setContentIntent(openPendingIntent)
            .build()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopKeepAwake()
    }
    
    fun isServiceRunning(): Boolean = isRunning
}
