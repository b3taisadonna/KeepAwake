package com.b3taisadonna.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PasswordViewModel(private val passwordDao: PasswordDao) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    
    val passwords: Flow<List<PasswordEntity>> = combine(
        _searchQuery,
        passwordDao.getAllPasswords()
    ) { query, allPasswords ->
        if (query.isBlank()) {
            allPasswords
        } else {
            allPasswords.filter { password ->
                password.title.contains(query, ignoreCase = true) ||
                password.username.contains(query, ignoreCase = true) ||
                (password.url?.contains(query, ignoreCase = true) ?: false)
            }
        }
    }
    
    fun search(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }
    
    fun addPassword(password: PasswordEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                passwordDao.insert(password)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error adding password"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updatePassword(password: PasswordEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                passwordDao.update(password.copy(updatedAt = System.currentTimeMillis()))
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error updating password"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deletePassword(password: PasswordEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                passwordDao.delete(password)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error deleting password"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun getPasswordById(id: Long): Flow<PasswordEntity?> {
        return passwordDao.getPasswordById(id)
            .flowOn(kotlinx.coroutines.Dispatchers.IO)
    }
}

class PasswordViewModelFactory(private val passwordDao: PasswordDao) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordViewModel(passwordDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
