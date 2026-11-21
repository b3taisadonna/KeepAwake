package com.b3taisadonna.passwordmanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(password: PasswordEntity): Long

    @Update
    suspend fun update(password: PasswordEntity)

    @Delete
    suspend fun delete(password: PasswordEntity)

    @Query("SELECT * FROM passwords ORDER BY title ASC")
    fun getAllPasswords(): Flow<List<PasswordEntity>>

    @Query("SELECT * FROM passwords WHERE id = :id")
    suspend fun getPasswordById(id: Long): PasswordEntity?

    @Query("SELECT * FROM passwords WHERE title LIKE '%' || :query || '%' OR username LIKE '%' || :query || '%' OR url LIKE '%' || :query || '%' ORDER BY title ASC")
    fun searchPasswords(query: String): Flow<List<PasswordEntity>>

    @Query("SELECT * FROM passwords ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestPassword(): PasswordEntity?
}
