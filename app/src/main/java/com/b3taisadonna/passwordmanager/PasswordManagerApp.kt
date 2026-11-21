package com.b3taisadonna.passwordmanager

import android.app.Application
import androidx.room.Room

class PasswordManagerApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    
    companion object {
        private const val PREFS_NAME = "PasswordManagerPrefs"
        private const val KEY_MASTER_PASSWORD_HASH = "master_password_hash"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        
        fun saveMasterPasswordHash(context: android.content.Context, hash: String) {
            val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
            prefs.edit().putString(KEY_MASTER_PASSWORD_HASH, hash).apply()
        }
        
        fun getMasterPasswordHash(context: android.content.Context): String? {
            val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
            return prefs.getString(KEY_MASTER_PASSWORD_HASH, null)
        }
        
        fun isFirstLaunch(context: android.content.Context): Boolean {
            val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
            return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
        }
        
        fun setFirstLaunch(context: android.content.Context, isFirst: Boolean) {
            val prefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
            prefs.edit().putBoolean(KEY_FIRST_LAUNCH, isFirst).apply()
        }
    }
}
