package com.example.observer.presenter

import com.example.observer.db.AppDatabase

interface IItemListPresenter {
    fun onGetAllItems(db:AppDatabase)
}