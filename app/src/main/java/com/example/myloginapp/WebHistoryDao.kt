package com.example.myloginapp;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WebHistoryDao {
    @Query("SELECT * FROM WebHistory")
    fun getAll(): List<WebHistory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(webEntity: WebHistory)

    @Query("DELETE FROM WebHistory")
    fun deleteAll()

    @Query("DELETE FROM WebHistory WHERE userId = :userId")
    fun delete(userId:Int)
}