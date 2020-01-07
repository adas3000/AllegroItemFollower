package com.example.observer.presenter

import com.example.observer.db.AppDatabase

interface IOnInternetPresenter {
    fun onAvailable(db:AppDatabase)
}