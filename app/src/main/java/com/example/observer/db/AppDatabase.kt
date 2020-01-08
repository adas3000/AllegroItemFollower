package com.example.observer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.observer.dao.AllegroItemDao
import com.example.observer.model.AllegroItem

@Database(entities = arrayOf(AllegroItem::class),version = 2) //todo turn into object
abstract class AppDatabase:RoomDatabase() {
    abstract fun allegroItemDao():AllegroItemDao
}