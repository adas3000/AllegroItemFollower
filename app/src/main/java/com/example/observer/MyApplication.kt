package com.example.observer

import android.app.Application
import com.example.observer.component.DaggerMainActivityComponent
import com.example.observer.component.MainActivityComponent
import com.example.observer.module.MainActivityModule

class MyApplication : Application() {

    lateinit var component: MainActivityComponent

    override fun onCreate() {

        super.onCreate()
        component = DaggerMainActivityComponent
                .builder()
                .mainActivityModule(MainActivityModule(applicationContext))
                .build()

        component.inject(this)
    }


}