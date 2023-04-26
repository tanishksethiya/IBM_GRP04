package com.example.myloginapp;

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WebHistory::class], version = 1, exportSchema = false)
abstract class WebHistoryDatabase : RoomDatabase() {
    abstract fun webHistoryDao(): WebHistoryDao

    companion object {
        private var INSTANCE: WebHistoryDatabase? = null

        fun getInstance(context: Context): WebHistoryDatabase? {
            if (INSTANCE == null) {
                synchronized(WebHistoryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WebHistoryDatabase::class.java, "WebHistory.db").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}

