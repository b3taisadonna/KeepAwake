package com.b3taisadonna.passwordmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if user is logged in (master password set)
        val isFirstLaunch = PasswordManagerApp.isFirstLaunch(this)
        
        val intent = if (isFirstLaunch) {
            // First launch - go to login to set master password
            Intent(this, LoginActivity::class.java)
        } else {
            // Not first launch - go to login to verify password
            Intent(this, LoginActivity::class.java)
        }
        
        startActivity(intent)
        finish()
    }
}
