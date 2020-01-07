package com.example.observer.presenter

import com.example.observer.db.AppDatabase
import com.example.observer.model.AllegroItem

interface IOnInternetPresenter {
    fun onAvailable(db:AppDatabase)
    fun doCheck(itemList:List<AllegroItem>)
}