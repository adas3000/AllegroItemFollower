package com.example.observer.module

import android.content.Context
import androidx.room.Room
import com.example.observer.db.AppDatabase
import com.example.observer.migrations.Migration_1
import com.example.observer.migrations.Migration_2
import com.example.observer.migrations.Migration_3
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule {

    val context:Context
    val dbName="allegroitemdb1"

    constructor(context: Context) {
        this.context = context
    }


    @Provides
    @Singleton
    fun getDatabaseInstance():AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
                .addMigrations(Migration_1, Migration_2, Migration_3)
                .build()
    }

    @Provides
    @Singleton
    fun getDatabaseName():String{
        return dbName
    }

}