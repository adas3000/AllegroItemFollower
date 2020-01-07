package com.example.observer.presenter

import com.example.observer.db.AppDatabase
import com.example.observer.view.ItemListView

class ItemListPresenter : IItemListPresenter {

    val itemListView:ItemListView


    constructor(itemListView:ItemListView){
        this.itemListView = itemListView
    }

    override fun onGetAllItems(db: AppDatabase) {

        


    }

}