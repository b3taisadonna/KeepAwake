package com.example.keepawake

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.keepawake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private var keepAwakeService: KeepAwakeService? = null
    private var isServiceBound = false
    private var isKeepAwakeActive = false
    
    // Permission request codes
    private companion object {
        private const val REQUEST_CODE_WAKE_LOCK = 1001
        private const val REQUEST_CODE_NOTIFICATION = 1002
    }
    
    // Service connection
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // Not used as we don't have a binder
        }
        
        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceBound = false
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Set up UI
        setupUI()
        
        // Check and request permissions
        checkPermissions()
        
        // Start service (but don't acquire wake lock yet)
        startService(Intent(this, KeepAwakeService::class.java))
        bindService(Intent(this, KeepAwakeService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
        isServiceBound = true
    }
    
    private fun setupUI() {
        binding.toggleButton.setOnClickListener {
            toggleKeepAwake()
        }
        
        binding.settingsButton.setOnClickListener {
            // Open settings (for now, just show a toast)
            Toast.makeText(this, "Settings coming soon!", Toast.LENGTH_SHORT).show()
        }
        
        updateUI()
    }
    
    private fun checkPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        
        // Check WAKE_LOCK permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WAKE_LOCK) 
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(android.Manifest.permission.WAKE_LOCK)
        }
        
        // Check FOREGROUND_SERVICE permission (only needed for Android 9+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.FOREGROUND_SERVICE) 
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(android.Manifest.permission.FOREGROUND_SERVICE)
            }
        }
        
        // Check NOTIFICATION permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) 
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_CODE_WAKE_LOCK
            )
        }
    }
    
    private fun toggleKeepAwake() {
        val serviceIntent = Intent(this, KeepAwakeService::class.java).apply {
            action = KeepAwakeApp.ACTION_TOGGLE
        }
        
        startService(serviceIntent)
        isKeepAwakeActive = !isKeepAwakeActive
        updateUI()
        
        // Update widget
        KeepAwakeWidget.updateAllWidgets(this, isKeepAwakeActive)
    }
    
    private fun updateUI() {
        if (isKeepAwakeActive) {
            binding.statusText.text = getString(R.string.screen_on)
            binding.statusText.setTextColor(ContextCompat.getColor(this, R.color.power_green))
            binding.toggleButton.text = getString(R.string.stop)
            binding.powerIcon.setImageResource(R.drawable.ic_power_on)
            binding.powerIcon.setColorFilter(ContextCompat.getColor(this, R.color.power_green))
        } else {
            binding.statusText.text = getString(R.string.screen_off)
            binding.statusText.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            binding.toggleButton.text = getString(R.string.start)
            binding.powerIcon.setImageResource(R.drawable.ic_power_off)
            binding.powerIcon.setColorFilter(ContextCompat.getColor(this, R.color.power_red))
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        when (requestCode) {
            REQUEST_CODE_WAKE_LOCK -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
                } else {
                    // Permission denied
                    Toast.makeText(this, "Permission denied. App may not work properly.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Unbind from service
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
        
        // Stop service if it's running
        val serviceIntent = Intent(this, KeepAwakeService::class.java)
        stopService(serviceIntent)
    }
}
