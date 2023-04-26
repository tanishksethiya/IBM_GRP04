package com.example.myloginapp;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WebHistory")
data class WebHistory(
    @PrimaryKey(autoGenerate = true) var userId: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name="status")val status:String
)