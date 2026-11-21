package com.b3taisadonna.passwordmanager

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val ITERATIONS = 10000
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = 16
    private const val IV_LENGTH = 16

    fun encrypt(data: String, password: String): String {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)
        
        val iv = ByteArray(IV_LENGTH)
        SecureRandom().nextBytes(iv)
        
        val secretKey = generateSecretKey(password, salt)
        
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        
        // Combine salt, iv, and encrypted data
        val combined = salt + iv + encryptedBytes
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    fun decrypt(encryptedData: String, password: String): String? {
        try {
            val combined = Base64.decode(encryptedData, Base64.DEFAULT)
            
            // Extract salt, iv, and encrypted data
            val salt = combined.copyOfRange(0, SALT_LENGTH)
            val iv = combined.copyOfRange(SALT_LENGTH, SALT_LENGTH + IV_LENGTH)
            val encryptedBytes = combined.copyOfRange(SALT_LENGTH + IV_LENGTH, combined.size)
            
            val secretKey = generateSecretKey(password, salt)
            
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
            
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun generateSecretKey(password: String, salt: ByteArray): SecretKey {
        val secretKeyFactory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val keySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val keyBytes = secretKeyFactory.generateSecret(keySpec).encoded
        return SecretKeySpec(keyBytes, "AES")
    }
}
