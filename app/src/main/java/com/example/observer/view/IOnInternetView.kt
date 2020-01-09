package com.example.observer.view

interface IOnInternetView {
    fun onPriceChanged(item_name:String,new_price:Float,item_url:String,uid:Int)
    fun onError(msg:String)
}