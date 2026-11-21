package com.b3taisadonna.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.b3taisadonna.passwordmanager.databinding.ActivityPasswordListBinding

class PasswordListActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPasswordListBinding
    private lateinit var viewModel: PasswordViewModel
    private lateinit var adapter: PasswordAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        
        val database = (application as PasswordManagerApp).database
        val passwordDao = database.passwordDao()
        
        viewModel = ViewModelProvider(
            this,
            PasswordViewModelFactory(passwordDao)
        )[PasswordViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        adapter = PasswordAdapter { password ->
            val intent = Intent(this, PasswordDetailActivity::class.java)
            intent.putExtra("PASSWORD_ID", password.id)
            startActivity(intent)
        }
        
        binding.passwordRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.passwordRecyclerView.adapter = adapter
    }
    
    private fun setupObservers() {
        viewModel.passwords.observe(this) { passwords ->
            adapter.submitList(passwords)
            binding.emptyText.visibility = if (passwords.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }
        
        viewModel.isLoading.observe(this) { isLoading ->
            // Show/hide loading indicator if needed
        }
        
        viewModel.error.observe(this) { error ->
            error?.let {
                // Show error message
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.addPasswordFab.setOnClickListener {
            startActivity(Intent(this, AddEditPasswordActivity::class.java))
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_password_list, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings
                true
            }
            R.id.action_logout -> {
                // Logout - go back to login
                PasswordManagerApp.setFirstLaunch(this, false)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh data
        viewModel.search("")
    }
}
