package com.b3taisadonna.passwordmanager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.b3taisadonna.passwordmanager.databinding.ActivityAddEditPasswordBinding

class AddEditPasswordActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddEditPasswordBinding
    private lateinit var viewModel: PasswordViewModel
    private var passwordId: Long? = null
    private var currentPassword: PasswordEntity? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        val database = (application as PasswordManagerApp).database
        val passwordDao = database.passwordDao()
        
        viewModel = ViewModelProvider(
            this,
            PasswordViewModelFactory(passwordDao)
        )[PasswordViewModel::class.java]
        
        passwordId = intent.getLongExtra("PASSWORD_ID", -1).takeIf { it != -1L }
        
        if (passwordId != null) {
            // Edit mode
            supportActionBar?.title = getString(R.string.edit)
            binding.saveButton.text = getString(R.string.update)
            loadPassword(passwordId!!)
        } else {
            // Add mode
            supportActionBar?.title = getString(R.string.add_password)
        }
        
        setupPasswordStrengthListener()
        setupClickListeners()
    }
    
    private fun loadPassword(id: Long) {
        viewModel.getPasswordById(id).observe(this) { password ->
            password?.let {
                currentPassword = it
                binding.titleInput.setText(it.title)
                binding.usernameInput.setText(it.username)
                binding.passwordInput.setText(it.password)
                binding.urlInput.setText(it.url ?: "")
                binding.notesInput.setText(it.notes ?: "")
            }
        }
    }
    
    private fun setupPasswordStrengthListener() {
        binding.passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                val strength = PasswordGenerator.getPasswordStrength(password)
                updatePasswordStrengthIndicator(strength)
            }
        })
    }
    
    private fun updatePasswordStrengthIndicator(strength: PasswordStrength) {
        val color = when (strength) {
            PasswordStrength.WEAK -> android.graphics.Color.RED
            PasswordStrength.MEDIUM -> android.graphics.Color.parseColor("#FFA500")
            PasswordStrength.STRONG -> android.graphics.Color.GREEN
            PasswordStrength.VERY_STRONG -> android.graphics.Color.parseColor("#008000")
        }
        val text = when (strength) {
            PasswordStrength.WEAK -> getString(R.string.weak)
            PasswordStrength.MEDIUM -> getString(R.string.medium)
            PasswordStrength.STRONG -> getString(R.string.strong)
            PasswordStrength.VERY_STRONG -> getString(R.string.very_strong)
        }
        binding.passwordStrengthText.text = "$text"
        binding.passwordStrengthText.setTextColor(color)
    }
    
    private fun setupClickListeners() {
        binding.generatePasswordButton.setOnClickListener {
            val generatedPassword = PasswordGenerator.generatePassword()
            binding.passwordInput.setText(generatedPassword)
        }
        
        binding.saveButton.setOnClickListener {
            savePassword()
        }
        
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }
    
    private fun savePassword() {
        val title = binding.titleInput.text.toString().trim()
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val url = binding.urlInput.text.toString().trim().takeIf { it.isNotEmpty() }
        val notes = binding.notesInput.text.toString().trim().takeIf { it.isNotEmpty() }
        
        if (title.isBlank()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (password.isBlank()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            return
        }
        
        val newPassword = PasswordEntity(
            id = passwordId ?: 0,
            title = title,
            username = username,
            password = password,
            url = url,
            notes = notes,
            createdAt = currentPassword?.createdAt ?: System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        
        if (passwordId != null) {
            viewModel.updatePassword(newPassword)
        } else {
            viewModel.addPassword(newPassword)
        }
        
        Toast.makeText(this, "Password saved", Toast.LENGTH_SHORT).show()
        finish()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
