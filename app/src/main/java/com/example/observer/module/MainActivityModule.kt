package com.example.observer.module

import android.content.Context
import androidx.room.Room
import com.example.observer.db.AppDatabase
import com.example.observer.migrations.Migration_1
import com.example.observer.migrations.Migration_2
import com.example.observer.migrations.Migration_3
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainActivityModule {

    val database: AppDatabase


    constructor(context: Context) {
        this.database = Room.databaseBuilder(context, AppDatabase::class.java, "allegroitemdb1")
                .addMigrations(Migration_1, Migration_2, Migration_3)
                .build()
    }

    @Provides
    fun getDatabaseInstance():AppDatabase{
        return database
    }

//    @Provides
//    @Named("notifyid")
//    fun providesNotifyId(): Int {
//        return 1
//    }


}