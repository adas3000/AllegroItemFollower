package com.example.observer.util

interface ItemAction {
    fun onUrlClick(url:String)
    fun onRemoveClick(id:Int)
}