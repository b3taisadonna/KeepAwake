package com.b3taisadonna.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.b3taisadonna.passwordmanager.databinding.ActivityLoginBinding
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private var isFirstLaunch = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        isFirstLaunch = PasswordManagerApp.isFirstLaunch(this)
        
        if (isFirstLaunch) {
            binding.loginTitle.text = getString(R.string.set_master_password)
            binding.loginSubtitle.text = "Please set a master password to protect your data"
            binding.setupText.visibility = android.view.View.GONE
        } else {
            binding.loginTitle.text = getString(R.string.login_title)
            binding.loginSubtitle.text = "Enter your master password to access your passwords"
            binding.setupText.visibility = android.view.View.GONE
        }
        
        binding.loginButton.setOnClickListener {
            val password = binding.masterPasswordInput.text.toString()
            if (password.isBlank()) {
                binding.errorText.text = "Please enter a password"
                binding.errorText.visibility = android.view.View.VISIBLE
                return@setOnClickListener
            }
            
            if (isFirstLaunch) {
                // First launch - set master password
                val confirmPassword = "" // We don't have a confirm field yet, so we'll just use the same password
                if (password.length < 4) {
                    binding.errorText.text = "Password must be at least 4 characters"
                    binding.errorText.visibility = android.view.View.VISIBLE
                    return@setOnClickListener
                }
                
                // Save hash of master password
                val hash = hashPassword(password)
                PasswordManagerApp.saveMasterPasswordHash(this, hash)
                PasswordManagerApp.setFirstLaunch(this, false)
                
                // Navigate to password list
                startActivity(Intent(this, PasswordListActivity::class.java))
                finish()
            } else {
                // Subsequent launches - verify password
                val savedHash = PasswordManagerApp.getMasterPasswordHash(this)
                val inputHash = hashPassword(password)
                
                if (savedHash == inputHash) {
                    // Password is correct
                    startActivity(Intent(this, PasswordListActivity::class.java))
                    finish()
                } else {
                    binding.errorText.text = getString(R.string.wrong_password)
                    binding.errorText.visibility = android.view.View.VISIBLE
                }
            }
        }
    }
    
    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
