package com.example.observer.view

interface IOnInternetView {
    fun onPriceChanged(item_name:String,new_price:Float)
}