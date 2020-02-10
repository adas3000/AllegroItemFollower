package com.example.observer.component

import android.content.Context
import com.example.observer.MainActivity
import com.example.observer.module.MainActivityModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(context: Context)
}