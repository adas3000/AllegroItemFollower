package com.example.observer.view

import com.example.observer.model.AllegroItem

interface IOnInternetView {
    fun onPriceChanged(allegroItem: AllegroItem)
    fun onError(msg:String)
}