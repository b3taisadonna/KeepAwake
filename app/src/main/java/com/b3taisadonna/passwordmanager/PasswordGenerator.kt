package com.b3taisadonna.passwordmanager

import java.security.SecureRandom

object PasswordGenerator {
    private const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
    private const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val DIGITS = "0123456789"
    private const val SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?"
    private const val ALL_CHARS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL
    
    private val random = SecureRandom()
    
    fun generatePassword(length: Int = 12, useUppercase: Boolean = true, useDigits: Boolean = true, useSpecial: Boolean = true): String {
        var chars = LOWERCASE
        if (useUppercase) chars += UPPERCASE
        if (useDigits) chars += DIGITS
        if (useSpecial) chars += SPECIAL
        
        val password = StringBuilder()
        repeat(length) {
            password.append(chars[random.nextInt(chars.length)])
        }
        return password.toString()
    }
    
    fun getPasswordStrength(password: String): PasswordStrength {
        var score = 0
        
        if (password.length >= 8) score += 1
        if (password.length >= 12) score += 1
        if (password.any { it in LOWERCASE }) score += 1
        if (password.any { it in UPPERCASE }) score += 1
        if (password.any { it in DIGITS }) score += 1
        if (password.any { it in SPECIAL }) score += 1
        
        return when (score) {
            0, 1, 2 -> PasswordStrength.WEAK
            3, 4 -> PasswordStrength.MEDIUM
            5 -> PasswordStrength.STRONG
            else -> PasswordStrength.VERY_STRONG
        }
    }
}

enum class PasswordStrength {
    WEAK, MEDIUM, STRONG, VERY_STRONG
}
