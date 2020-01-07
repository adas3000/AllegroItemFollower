package com.example.observer.view

import com.example.observer.model.AllegroItem

interface ItemListView {
    fun onError(msg:String)
    fun onFinish(list:List<AllegroItem>)
}