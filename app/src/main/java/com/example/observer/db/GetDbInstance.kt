package com.example.observer.db

import android.content.Context
import androidx.room.Room
import com.example.observer.migrations.Migration_1

object GetDbInstance {
    fun getDb(context:Context):AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "allegroitemdb1").addMigrations(Migration_1).build()
    }
}