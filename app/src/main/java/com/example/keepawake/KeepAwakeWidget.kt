package com.example.keepawake

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.content.ContextCompat

class KeepAwakeWidget : AppWidgetProvider() {
    
    companion object {
        private const val ACTION_TOGGLE_WIDGET = "com.example.keepawake.ACTION_TOGGLE_WIDGET"
        
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            isActive: Boolean
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_keepawake)
            
            // Update UI based on state
            if (isActive) {
                views.setTextViewText(R.id.widget_status, context.getString(R.string.screen_on))
                views.setTextColor(R.id.widget_status, ContextCompat.getColor(context, R.color.power_green))
                views.setImageViewResource(R.id.widget_icon, R.drawable.ic_power_on)
                views.setTextViewText(R.id.widget_button, context.getString(R.string.stop))
            } else {
                views.setTextViewText(R.id.widget_status, context.getString(R.string.screen_off))
                views.setTextColor(R.id.widget_status, ContextCompat.getColor(context, R.color.text_primary))
                views.setImageViewResource(R.id.widget_icon, R.drawable.ic_power_off)
                views.setTextViewText(R.id.widget_button, context.getString(R.string.start))
            }
            
            // Set up click listener for the button
            val toggleIntent = Intent(context, KeepAwakeWidget::class.java).apply {
                action = ACTION_TOGGLE_WIDGET
            }
            
            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    appWidgetId,
                    toggleIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            } else {
                PendingIntent.getBroadcast(
                    context,
                    appWidgetId,
                    toggleIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent)
            
            // Set up click listener for the whole widget
            val openAppIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            
            val openPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(
                    context,
                    appWidgetId,
                    openAppIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            } else {
                PendingIntent.getActivity(
                    context,
                    appWidgetId,
                    openAppIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            
            views.setOnClickPendingIntent(R.id.widget_container, openPendingIntent)
            
            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        
        fun updateAllWidgets(context: Context, isActive: Boolean) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, KeepAwakeWidget::class.java)
            )
            
            widgetIds.forEach { appWidgetId ->
                updateAppWidget(context, appWidgetManager, appWidgetId, isActive)
            }
        }
    }
    
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Update all widgets with default state (off)
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(context, appWidgetManager, appWidgetId, false)
        }
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        
        if (intent.action == ACTION_TOGGLE_WIDGET) {
            // Toggle the keep awake state
            val serviceIntent = Intent(context, KeepAwakeService::class.java).apply {
                action = KeepAwakeApp.ACTION_TOGGLE
            }
            context.startService(serviceIntent)
            
            // Update all widgets
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, KeepAwakeWidget::class.java)
            )
            
            // We need to check the current state to update the widget
            // For simplicity, we'll just toggle the state in the widget
            // In a real app, you would check the actual service state
            widgetIds.forEach { appWidgetId ->
                val views = RemoteViews(context.packageName, R.layout.widget_keepawake)
                val currentText = views.getTextViewText(R.id.widget_button)
                val isActive = currentText == context.getString(R.string.stop)
                updateAppWidget(context, appWidgetManager, appWidgetId, !isActive)
            }
        }
    }
    
    override fun onEnabled(context: Context) {
        // Called when the first widget is placed
        super.onEnabled(context)
    }
    
    override fun onDisabled(context: Context) {
        // Called when the last widget is removed
        super.onDisabled(context)
    }
}
