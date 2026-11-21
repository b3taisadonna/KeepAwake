package com.b3taisadonna.passwordmanager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.b3taisadonna.passwordmanager.databinding.ActivityPasswordDetailBinding

class PasswordDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPasswordDetailBinding
    private lateinit var viewModel: PasswordViewModel
    private var passwordId: Long = -1
    private var currentPassword: PasswordEntity? = null
    private var isPasswordVisible = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        val database = (application as PasswordManagerApp).database
        val passwordDao = database.passwordDao()
        
        viewModel = ViewModelProvider(
            this,
            PasswordViewModelFactory(passwordDao)
        )[PasswordViewModel::class.java]
        
        passwordId = intent.getLongExtra("PASSWORD_ID", -1)
        
        if (passwordId == -1L) {
            Toast.makeText(this, "Invalid password ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        loadPassword(passwordId)
        setupClickListeners()
    }
    
    private fun loadPassword(id: Long) {
        viewModel.getPasswordById(id).observe(this) { password ->
            password?.let {
                currentPassword = it
                displayPassword(it)
            }
        }
    }
    
    private fun displayPassword(password: PasswordEntity) {
        binding.detailTitle.text = password.title
        binding.detailUsername.text = password.username
        binding.detailPassword.text = "••••••••"
        binding.detailUrl.text = password.url ?: "Not specified"
        binding.detailNotes.text = password.notes ?: "No notes"
    }
    
    private fun setupClickListeners() {
        binding.copyUsernameButton.setOnClickListener {
            currentPassword?.let { password ->
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("username", password.username)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, R.string.username_copied, Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.copyPasswordButton.setOnClickListener {
            currentPassword?.let { password ->
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("password", password.password)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, R.string.password_copied, Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.togglePasswordButton.setOnClickListener {
            currentPassword?.let { password ->
                isPasswordVisible = !isPasswordVisible
                if (isPasswordVisible) {
                    binding.detailPassword.text = password.password
                    binding.togglePasswordButton.setImageResource(R.drawable.ic_visibility_off)
                } else {
                    binding.detailPassword.text = "••••••••"
                    binding.togglePasswordButton.setImageResource(R.drawable.ic_visibility)
                }
            }
        }
        
        binding.editButton.setOnClickListener {
            currentPassword?.let { password ->
                val intent = Intent(this, AddEditPasswordActivity::class.java)
                intent.putExtra("PASSWORD_ID", password.id)
                startActivity(intent)
                finish()
            }
        }
        
        binding.deleteButton.setOnClickListener {
            showDeleteConfirmation()
        }
    }
    
    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle(R.string.are_you_sure)
            .setMessage(R.string.delete_confirmation)
            .setPositiveButton(R.string.yes) { _, _ ->
                currentPassword?.let { password ->
                    viewModel.deletePassword(password)
                    Toast.makeText(this, "Password deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
