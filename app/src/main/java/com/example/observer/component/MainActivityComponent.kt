package com.example.observer.component

import android.content.Context
import com.example.observer.MainActivity
import com.example.observer.db.AppDatabase
import com.example.observer.module.MainActivityModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [MainActivityModule::class])
@Singleton
interface MainActivityComponent {
    fun inject(context: Context)
    fun getDatabaseInstance():AppDatabase
    fun getDatabaseName():String
}